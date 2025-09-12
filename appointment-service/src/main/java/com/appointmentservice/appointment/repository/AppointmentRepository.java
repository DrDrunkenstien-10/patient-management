package com.appointmentservice.appointment.repository;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.appointmentservice.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {
        Optional<Appointment> findTop1ByDoctorIdAndSlotIdAndStatusInOrderByRankAsc(UUID doctorId, UUID slotId,
                        List<String> statuses);

        Optional<Appointment> findTop1ByDoctorIdAndSlotIdOrderByRankDesc(UUID doctorId, UUID slotId);

        boolean existsByDoctorIdAndPatientIdAndSlotIdAndAppointmentDate(UUID doctorId, UUID slotId,
                        UUID patientId,
                        LocalDate appointmentDate);

        boolean existsByAppointmentId(UUID appointmentId);

        boolean existsByDoctorIdAndSlotIdAndAppointmentDateAndAppointmentTime(UUID doctorId, UUID slotId,
                        LocalDate appointmentDate, LocalTime appointmentTime);

        Optional<Appointment> findTopByDoctorIdOrderByRankDesc(UUID doctorId);
}
