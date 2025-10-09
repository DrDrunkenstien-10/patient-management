package com.appointmentservice.appointment.repository;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.appointmentservice.appointment.enums.AppointmentStatus;
import com.appointmentservice.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {
        Optional<Appointment> findTop1ByDoctorIdAndSlotIdAndStatusInOrderByRankAsc(UUID doctorId, UUID slotId,
                        List<String> statuses);

        Optional<Appointment> findTop1ByDoctorIdAndSlotIdOrderByRankDesc(UUID doctorId, UUID slotId);

        // Ignore CANCELLED and RESCHEDULED appointments
        boolean existsByDoctorIdAndPatientIdAndSlotIdAndAppointmentDateAndStatusNotIn(
                        UUID doctorId,
                        UUID patientId,
                        UUID slotId,
                        LocalDate date,
                        List<AppointmentStatus> excludedStatuses);

        boolean existsByDoctorIdAndSlotIdAndAppointmentDateAndAppointmentTimeAndStatusNotIn(
                        UUID doctorId,
                        UUID slotId,
                        LocalDate date,
                        LocalTime time,
                        List<AppointmentStatus> excludedStatuses);

        Optional<Appointment> findTopByDoctorIdOrderByRankDesc(UUID doctorId);

        boolean existsByAppointmentDateAndSlotIdAndDoctorIdAndAppointmentTime(LocalDate date, UUID slotId,
                        UUID doctorId,
                        LocalTime time);

        Optional<Appointment> findByAppointmentDateAndSlotIdAndDoctorIdAndAppointmentTime(
                        LocalDate date, UUID slotId, UUID doctorId, LocalTime time);

}
