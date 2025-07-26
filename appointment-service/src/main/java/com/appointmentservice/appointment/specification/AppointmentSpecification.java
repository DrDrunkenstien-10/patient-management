package com.appointmentservice.appointment.specification;

import org.springframework.data.jpa.domain.Specification;

import com.appointmentservice.appointment.enums.AppointmentStatus;
import com.appointmentservice.appointment.model.Appointment;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentSpecification {

    public static Specification<Appointment> getAppointmentSpecification(String category, String value) {
        return (Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            if (value == null || value.trim().isEmpty()) {
                return cb.conjunction(); // return all records if no filter
            }

            switch (category.toLowerCase()) {
                case "appointment_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("appointmentId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // invalid UUID
                    }

                case "doctor_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("doctorId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction();
                    }

                case "patient_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("patientId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction();
                    }

                case "slot_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("slotId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction();
                    }

                case "appointment_date":
                    try {
                        LocalDate date = LocalDate.parse(value);
                        return cb.equal(root.get("appointmentDate"), date);
                    } catch (Exception ex) {
                        return cb.disjunction(); // invalid date
                    }

                case "appointment_time":
                    try {
                        LocalTime time = LocalTime.parse(value);
                        return cb.equal(root.get("appointmentTime"), time);
                    } catch (Exception ex) {
                        return cb.disjunction(); // invalid time
                    }

                case "status":
                    try {
                        AppointmentStatus statusEnum = AppointmentStatus.valueOf(value.toUpperCase());
                        return cb.equal(root.get("status"), statusEnum);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // invalid status
                    }

                case "rank":
                    try {
                        int rank = Integer.parseInt(value);
                        return cb.equal(root.get("rank"), rank);
                    } catch (NumberFormatException ex) {
                        return cb.disjunction(); // invalid integer
                    }

                default:
                    return cb.conjunction(); // return all if unknown category
            }
        };
    }
}
