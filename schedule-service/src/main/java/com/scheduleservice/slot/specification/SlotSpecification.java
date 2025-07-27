package com.scheduleservice.slot.specification;

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
                return cb.conjunction(); // Return all records if value is empty
            }

            switch (category.toLowerCase()) {
                case "name":
                    return cb.like(root.get("name"), "%" + value + "%");

                case "slot_id":
                    return cb.equal(root.get("slotId"), UUID.fromString(value));

                case "start_time":
                    return cb.equal(root.get("startTime").as(String.class), value);

                case "end_time":
                    return cb.equal(root.get("endTime").as(String.class), value);

                case "capacity":
                    return cb.equal(root.get("capacity"), Integer.parseInt(value));

                case "session_duration":
                    return cb.equal(root.get("sessionDuration"), Integer.parseInt(value));

                case "doctor_id":
                    return cb.equal(root.get("doctorId"), UUID.fromString(value));

                case "created_at":
                    return cb.equal(root.get("createdAt").as(String.class), value);
                
                case "updated_at":
                    return cb.equal(root.get("updatedAt").as(String.class), value);

                default:
                    return cb.disjunction(); // Invalid category, return no results
            }
        };
    }

}
