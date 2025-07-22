package com.scheduleservice.slot.specification;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.scheduleservice.slot.model.Slot;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class SlotSpecification {
    public static Specification<Slot> getSlotSpecification(String category, String value) {
        return (Root<Slot> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            if (value == null || value.trim().isEmpty()) {
                return cb.conjunction(); // return all records if value is empty
            }

            switch (category.toLowerCase()) {
                case "slot_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("slotId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // invalid UUID
                    }

                case "name":
                    return cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");

                case "doctor_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("doctorId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // invalid UUID
                    }

                case "capacity":
                    try {
                        Integer capacity = Integer.parseInt(value);
                        return cb.equal(root.get("capacity"), capacity);
                    } catch (NumberFormatException ex) {
                        return cb.disjunction(); // invalid number
                    }

                case "session_duration":
                    try {
                        Integer duration = Integer.parseInt(value);
                        return cb.equal(root.get("sessionDuration"), duration);
                    } catch (NumberFormatException ex) {
                        return cb.disjunction(); // invalid number
                    }

                case "start_time":
                    try {
                        LocalTime startTime = LocalTime.parse(value);
                        return cb.equal(root.get("startTime"), startTime);
                    } catch (Exception ex) {
                        return cb.disjunction(); // invalid time format
                    }

                case "end_time":
                    try {
                        LocalTime endTime = LocalTime.parse(value);
                        return cb.equal(root.get("endTime"), endTime);
                    } catch (Exception ex) {
                        return cb.disjunction(); // invalid time format
                    }

                default:
                    return cb.conjunction(); // no filtering for unknown category
            }
        };
    }
}
