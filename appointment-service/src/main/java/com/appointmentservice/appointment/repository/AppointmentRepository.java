package com.appointmentservice.appointment.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointmentservice.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Optional<Appointment> findTop1ByDoctorIdAndSlotIdOrderByRankDesc(UUID doctorId, UUID slotId);

}
