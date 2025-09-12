package com.appointmentservice.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appointmentservice.appointment.client.dto.AvailabilityDTO;
import com.appointmentservice.appointment.client.dto.DoctorDTO;
import com.appointmentservice.appointment.client.dto.PatientDTO;
import com.appointmentservice.appointment.client.dto.SlotDTO;
import com.appointmentservice.appointment.client.service.AvailabilityServiceClient;
import com.appointmentservice.appointment.client.service.DoctorServiceClient;
import com.appointmentservice.appointment.client.service.PatientServiceClient;
import com.appointmentservice.appointment.client.service.SlotServiceClient;
import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.dto.AppointmentResponseDTO;
import com.appointmentservice.appointment.dto.PaginatedResponseDTO;
import com.appointmentservice.appointment.enums.AppointmentStatus;
import com.appointmentservice.appointment.exception.SlotCapacityExceededException;
import com.appointmentservice.appointment.exception.AppointmentNotFoundException;
import com.appointmentservice.appointment.mapper.AppointmentMapper;
import com.appointmentservice.appointment.model.Appointment;
import com.appointmentservice.appointment.repository.AppointmentRepository;
import com.appointmentservice.appointment.specification.AppointmentSpecification;
import com.appointmentservice.appointment.validator.AppointmentValidator;

@Service
public class AppointmentService {
	private final AppointmentRepository appointmentRepository;
	private final SlotServiceClient slotServiceClient;
	private final PatientServiceClient patientServiceClient;
	private final DoctorServiceClient doctorServiceClient;
	private final AppointmentValidator appointmentValidator;
	private final AvailabilityServiceClient availabilityServiceClient;

	public AppointmentService(AppointmentRepository appointmentRepository, SlotServiceClient slotServiceClient,
			PatientServiceClient patientServiceClient, DoctorServiceClient doctorServiceClient,
			AppointmentValidator appointmentValidator,
			AvailabilityServiceClient availabilityServiceClient) {
		this.appointmentRepository = appointmentRepository;
		this.slotServiceClient = slotServiceClient;
		this.patientServiceClient = patientServiceClient;
		this.doctorServiceClient = doctorServiceClient;
		this.appointmentValidator = appointmentValidator;
		this.availabilityServiceClient = availabilityServiceClient;
	}

	public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
		appointmentValidator.validateForCreation(appointmentRequestDTO);

		List<String> statuses = Arrays.asList("RESCHEDULED", "CANCELLED");
		Optional<Appointment> existingResheduledOrCancelledAppointment = appointmentRepository
				.findTop1ByDoctorIdAndSlotIdAndStatusInOrderByRankAsc(
						appointmentRequestDTO.getDoctorId(),
						appointmentRequestDTO.getSlotId(),
						statuses);

		Optional<Appointment> existingAppointmentOpt = appointmentRepository
				.findTop1ByDoctorIdAndSlotIdOrderByRankDesc(
						appointmentRequestDTO.getDoctorId(),
						appointmentRequestDTO.getSlotId());

		SlotDTO slotDTO = slotServiceClient.getSlotById(appointmentRequestDTO.getSlotId());
		PatientDTO patientDTO = patientServiceClient.getPatientById(appointmentRequestDTO.getPatientId());
		DoctorDTO doctorDTO = doctorServiceClient.getDoctorById(appointmentRequestDTO.getDoctorId());

		if (existingResheduledOrCancelledAppointment.isPresent()) {
			Appointment appointment = updateAppointmentIfExistingResheduledOrCancelled(
					appointmentRequestDTO,
					existingResheduledOrCancelledAppointment.get());
			return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
		}

		Appointment appointment;
		if (existingAppointmentOpt.isPresent()) {
			appointment = createSubsequentAppointment(existingAppointmentOpt.get(), appointmentRequestDTO,
					slotDTO);
		} else {
			appointment = createFirstAppointment(appointmentRequestDTO, slotDTO);
		}

		return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
	}

	private Appointment createFirstAppointment(AppointmentRequestDTO appointmentRequestDTO, SlotDTO slotDTO) {
		// LocalTime startTime = LocalTime.parse("11:00:00");
		LocalTime startTime = LocalTime.parse(slotDTO.getStartTime());

		appointmentRequestDTO.setAppointmentTime(startTime);
		appointmentRequestDTO.setAppointmentStatus(AppointmentStatus.NOT_VISITED);
		appointmentRequestDTO.setRank(1);

		return appointmentRepository.save(AppointmentMapper.toModel(appointmentRequestDTO));
	}

	private Appointment createSubsequentAppointment(Appointment existingAppointment,
			AppointmentRequestDTO appointmentRequestDTO, SlotDTO slotDTO) {

		LocalTime appointmentTime = existingAppointment.getAppointmentTime();
		int rank = existingAppointment.getRank();

		int sessionDuration = slotDTO.getSessionDuration();
		int capacity = slotDTO.getCapacity();

		if (rank < capacity) {

			// Increment appointment time by session duration
			appointmentTime = appointmentTime.plusMinutes(sessionDuration);

			appointmentRequestDTO.setAppointmentTime(appointmentTime);
			appointmentRequestDTO.setAppointmentStatus(AppointmentStatus.NOT_VISITED);
			appointmentRequestDTO.setRank(rank + 1);

			return appointmentRepository.save(AppointmentMapper.toModel(appointmentRequestDTO));
		}

		else {
			UUID doctorId = existingAppointment.getDoctorId();
			UUID slotId = existingAppointment.getSlotId();
			LocalDate date = existingAppointment.getAppointmentDate();
			boolean availability = false;
			String unAvailabilityReason = "Slot full";
			UUID availabilityId = availabilityServiceClient.getAvailabilityId(doctorId, slotId, date);

			AvailabilityDTO availabilityDTO = new AvailabilityDTO();

			availabilityDTO.setAvailability(availability);
			availabilityDTO.setUnavailabilityReason(unAvailabilityReason);

			availabilityServiceClient.updateAvailabilityStatus(availabilityDTO, availabilityId);

			throw new SlotCapacityExceededException(
					"No available appointments: slot capacity of " + capacity
							+ " reached for slotId "
							+ appointmentRequestDTO.getSlotId());
		}
	}

	public Appointment updateAppointmentIfExistingResheduledOrCancelled(AppointmentRequestDTO appointmentRequestDTO,
			Appointment existingResheduledOrCancelledAppointment) {

		existingResheduledOrCancelledAppointment.setPatientId(appointmentRequestDTO.getPatientId());
		existingResheduledOrCancelledAppointment.setStatus(AppointmentStatus.NOT_VISITED);

		return appointmentRepository.save(existingResheduledOrCancelledAppointment);

	}

	public PaginatedResponseDTO<AppointmentResponseDTO> getAppointments(int page, int size,
			String sortBy) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		Page<Appointment> appointments = appointmentRepository.findAll(pageable);

		List<AppointmentResponseDTO> appointmentResponseDTOs = appointments.stream()
				.map(appointment -> {
					SlotDTO slotDTO = slotServiceClient.getSlotById(appointment.getSlotId());
					PatientDTO patientDTO = patientServiceClient
							.getPatientById(appointment.getPatientId());
					DoctorDTO doctorDTO = doctorServiceClient
							.getDoctorById(appointment.getDoctorId());
					return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
				})
				.toList();

		return new PaginatedResponseDTO<>(
				appointmentResponseDTOs,
				appointments.getNumber(),
				appointments.getSize(),
				appointments.getTotalElements(),
				appointments.getTotalPages(),
				appointments.isLast(),
				appointments.isFirst());
	}

	public PaginatedResponseDTO<AppointmentResponseDTO> filterAppointments(
			String category,
			String value,
			String direction,
			int page,
			int size,
			String sortBy) {

		appointmentValidator.validateFilterCategory(category);

		Sort sort = direction.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Specification<Appointment> spec = AppointmentSpecification
				.getAppointmentSpecification(category, value);

		Page<Appointment> appointments = appointmentRepository.findAll(spec, pageable);

		List<AppointmentResponseDTO> appointmentResponseDTOs = appointments.stream()
				.map(appointment -> {
					SlotDTO slotDTO = slotServiceClient.getSlotById(appointment.getSlotId());
					PatientDTO patientDTO = patientServiceClient
							.getPatientById(appointment.getPatientId());
					DoctorDTO doctorDTO = doctorServiceClient
							.getDoctorById(appointment.getDoctorId());
					return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
				})
				.toList();

		return new PaginatedResponseDTO<>(
				appointmentResponseDTOs,
				appointments.getNumber(),
				appointments.getSize(),
				appointments.getTotalElements(),
				appointments.getTotalPages(),
				appointments.isLast(),
				appointments.isFirst());
	}

	public PaginatedResponseDTO<AppointmentResponseDTO> getUpcomingAppointments(
			UUID patientId,
			int page,
			int size,
			String category,
			String value) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").ascending());
		LocalDate today = LocalDate.now();

		Specification<Appointment> spec = (root, query, cb) -> cb.and(
				cb.equal(root.get("patientId"), patientId),
				root.get("status").in(AppointmentStatus.NOT_VISITED, AppointmentStatus.RESCHEDULED),
				cb.greaterThanOrEqualTo(root.get("appointmentDate"), today));

		// ✅ If filter params are provided, extend spec
		if (category != null && value != null) {
			spec = spec.and(AppointmentSpecification.getAppointmentSpecification(category, value));
		}

		Page<Appointment> appointments = appointmentRepository.findAll(spec, pageable);

		List<AppointmentResponseDTO> appointmentResponseDTOs = appointments.stream()
				.map(appointment -> {
					SlotDTO slotDTO = slotServiceClient.getSlotById(appointment.getSlotId());
					PatientDTO patientDTO = patientServiceClient.getPatientById(appointment.getPatientId());
					DoctorDTO doctorDTO = doctorServiceClient.getDoctorById(appointment.getDoctorId());
					return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
				})
				.toList();

		return new PaginatedResponseDTO<>(
				appointmentResponseDTOs,
				appointments.getNumber(),
				appointments.getSize(),
				appointments.getTotalElements(),
				appointments.getTotalPages(),
				appointments.isLast(),
				appointments.isFirst());
	}

	public PaginatedResponseDTO<AppointmentResponseDTO> getPastAppointments(
			UUID patientId,
			int page,
			int size,
			String category,
			String value) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").ascending());
		LocalDate today = LocalDate.now();

		Specification<Appointment> spec = (root, query, cb) -> cb.and(
				cb.equal(root.get("patientId"), patientId),
				root.get("status").in(AppointmentStatus.VISITED, AppointmentStatus.CANCELLED),
				cb.lessThanOrEqualTo(root.get("appointmentDate"), today));

		// ✅ If filter params are provided, extend spec
		if (category != null && value != null) {
			spec = spec.and(AppointmentSpecification.getAppointmentSpecification(category, value));
		}

		Page<Appointment> appointments = appointmentRepository.findAll(spec, pageable);

		List<AppointmentResponseDTO> appointmentResponseDTOs = appointments.stream()
				.map(appointment -> {
					SlotDTO slotDTO = slotServiceClient.getSlotById(appointment.getSlotId());
					PatientDTO patientDTO = patientServiceClient
							.getPatientById(appointment.getPatientId());
					DoctorDTO doctorDTO = doctorServiceClient
							.getDoctorById(appointment.getDoctorId());
					return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
				})
				.toList();

		return new PaginatedResponseDTO<>(
				appointmentResponseDTOs,
				appointments.getNumber(),
				appointments.getSize(),
				appointments.getTotalElements(),
				appointments.getTotalPages(),
				appointments.isLast(),
				appointments.isFirst());
	}

	public AppointmentResponseDTO updateAppointment(UUID appointmentId,
			AppointmentRequestDTO appointmentRequestDTO) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(
						() -> new AppointmentNotFoundException(
								"appointment with Id" + appointmentId + "not found"));

		appointment.setAppointmentId(appointmentId);
		appointment.setDoctorId(appointmentRequestDTO.getDoctorId());
		appointment.setPatientId(appointmentRequestDTO.getPatientId());
		appointment.setSlotId(appointmentRequestDTO.getSlotId());
		appointment.setAppointmentTime(appointmentRequestDTO.getAppointmentTime());
		appointment.setAppointmentDate(appointmentRequestDTO.getAppointmentDate());
		appointment.setStatus(appointmentRequestDTO.getAppointmentStatus());
		appointment.setRank(appointmentRequestDTO.getRank());

		appointmentRepository.save(appointment);

		return AppointmentMapper.toDto(appointment,
				slotServiceClient.getSlotById(appointmentRequestDTO.getSlotId()),
				doctorServiceClient.getDoctorById(appointmentRequestDTO.getDoctorId()),
				patientServiceClient.getPatientById(appointmentRequestDTO.getPatientId()));
	}

	public AppointmentResponseDTO patchAppointmentStatus(UUID appointmentId, AppointmentStatus status) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(
						() -> new AppointmentNotFoundException(
								"appointment with Id" + appointmentId + "not found"));
		appointment.setAppointmentId(appointmentId);
		appointment.setStatus(status);
		appointmentRepository.save(appointment);

		SlotDTO slotDTO = slotServiceClient.getSlotById(appointment.getSlotId());
		PatientDTO patientDTO = patientServiceClient.getPatientById(appointment.getPatientId());
		DoctorDTO doctorDTO = doctorServiceClient.getDoctorById(appointment.getDoctorId());

		return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
	}

	public List<AppointmentResponseDTO> getAppointments() {
		List<Appointment> appointments = appointmentRepository.findAll();

		// Map each Appointment to AppointmentResponseDTO
		return appointments.stream()
				.map(appointment -> {
					SlotDTO slotDTO = slotServiceClient.getSlotById(appointment.getSlotId());
					PatientDTO patientDTO = patientServiceClient
							.getPatientById(appointment.getPatientId());
					DoctorDTO doctorDTO = doctorServiceClient
							.getDoctorById(appointment.getDoctorId());
					return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
				})
				.toList();
	}

	public AppointmentResponseDTO getAppointmentByDoctorId(UUID doctorId) {
		Appointment appointment = appointmentRepository.findTopByDoctorIdOrderByRankDesc(doctorId)
				.orElseThrow(
						() -> new AppointmentNotFoundException(
								"No appointment found for doctor with Id " + doctorId));

		SlotDTO slotDTO = slotServiceClient.getSlotById(appointment.getSlotId());
		PatientDTO patientDTO = patientServiceClient.getPatientById(appointment.getPatientId());
		DoctorDTO doctorDTO = doctorServiceClient.getDoctorById(appointment.getDoctorId());

		return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
	}

	@Transactional
	public void deleteAppointment(UUID appointmentId) {
		if (!appointmentRepository.existsById(appointmentId)) {
			throw new AppointmentNotFoundException("appointment with Id " + appointmentId + "not found");
		}
		appointmentRepository.deleteById(appointmentId);
	}
}