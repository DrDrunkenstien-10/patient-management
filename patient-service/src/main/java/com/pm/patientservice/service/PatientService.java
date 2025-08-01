// Note: Uncomment the commented code to use Kafka broker and Grpc.
package com.pm.patientservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import com.pm.patientservice.client.dto.AppointmentDTO;
import com.pm.patientservice.client.dto.UserRequestDTO;
import com.pm.patientservice.client.service.AppointmentServiceClient;
import com.pm.patientservice.client.service.UserServiceClient;
import com.pm.patientservice.dto.PaginatedResponseDTO;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.PatientNotFoundException;
// import com.pm.patientservice.grpc.BillingServiceGrpcClient;
// import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.specification.PatientSpecification;
import com.pm.patientservice.validator.PatientValidator;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private PatientValidator patientValidator;
    private AppointmentServiceClient appointmentServiceClient;
    private UserServiceClient userServiceClient;
    // private BillingServiceGrpcClient billingServiceGrpcClient;
    // private KafkaProducer kafkaProducer;

    // public PatientService(PatientRepository patientRepository,
    // BillingServiceGrpcClient billingServiceGrpcClient,
    // KafkaProducer kafkaProducer) {
    // this.patientRepository = patientRepository;
    // this.billingServiceGrpcClient = billingServiceGrpcClient;
    // this.kafkaProducer = kafkaProducer;
    // }

    public PatientService(PatientRepository patientRepository,
            PatientValidator patientValidator,
            AppointmentServiceClient appointmentServiceClient,
            UserServiceClient userServiceClient) {
        this.patientRepository = patientRepository;
        this.patientValidator = patientValidator;
        this.appointmentServiceClient = appointmentServiceClient;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        try {
            patientValidator.validateForCreation(patientRequestDTO);

            Patient newPatient = patientRepository.save(
                    PatientMapper.toModel(patientRequestDTO));

            // billingServiceGrpcClient.createBillingAccount(newPatient.getPatientId().toString(),
            // newPatient.getName(), newPatient.getEmail());

            // kafkaProducer.sendEvent(newPatient);
            userServiceClient.createUser(
                    new UserRequestDTO(patientRequestDTO.getEmail(), patientRequestDTO.getPassword(), "PATIENT"));

            return PatientMapper.toDTO(newPatient);
        } catch (Exception e) {
            throw new RuntimeException("Error creating patient: " + e.getMessage(), e);
        }
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
                .toList();

        return patientResponseDTOs;
    }

    public PaginatedResponseDTO<PatientResponseDTO> filterDoctors(
            String category,
            String value,
            String direction,
            int page,
            int size,
            String sortBy) {

        patientValidator.validateFilterCategory(category);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Patient> spec = PatientSpecification.getPatientSpecification(category, value);

        Page<Patient> patients = patientRepository.findAll(spec, pageable);

        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
                .toList();

        return new PaginatedResponseDTO<>(
                patientResponseDTOs,
                patients.getNumber(),
                patients.getSize(),
                patients.getTotalElements(),
                patients.getTotalPages(),
                patients.isLast(),
                patients.isFirst());
    }

    public PaginatedResponseDTO<PatientResponseDTO> getPatients(int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, 10);
        Page<Patient> patients = patientRepository.findAll(pageable);

        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
                .toList();

        return new PaginatedResponseDTO<>(
                patientResponseDTOs,
                patients.getNumber(),
                patients.getSize(),
                patients.getTotalElements(),
                patients.getTotalPages(),
                patients.isLast(),
                patients.isFirst());
    }

    public List<PatientResponseDTO> getPatientByDoctorId(UUID doctorId) {
        List<AppointmentDTO> appointments = appointmentServiceClient.getAppointmentsByDoctorId(doctorId);
        if (appointments.isEmpty()) {
            throw new PatientNotFoundException("No appointments found for doctor with ID: " + doctorId);
        }
        List<UUID> patientIds = appointments.stream()
                .map(AppointmentDTO::getPatientId)
                .distinct()
                .toList();

        List<Patient> patients = patientRepository.findAllById(patientIds);
        if (patients.isEmpty()) {
            throw new PatientNotFoundException("No patients found for doctor with ID: " + doctorId);
        }
        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientResponseDTO getPatientById(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID patientId, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));

        patientValidator.validateForUpdate(patientId, patientRequestDTO);

        patient.setName(patientRequestDTO.getName());
        patient.setGender(patientRequestDTO.getGender());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setAadhaarNumber(patientRequestDTO.getAadhaarNumber());
        patient.setContactPhone(patientRequestDTO.getContactPhone());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setMedicalHistory(patientRequestDTO.getMedicalHistory());
        patient.setAllergies(patientRequestDTO.getAllergies());
        patient.setMedications(patientRequestDTO.getMedications());
        patient.setConsents(patientRequestDTO.getConsents());
        patient.setEmergencyContact(patientRequestDTO.getEmergencyContact());

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

    public boolean isPatientExists(UUID patientId) {
        return patientRepository.existsById(patientId);
    }
}
