package com.adminservice.doctor.specification;

import com.adminservice.doctor.enums.Gender;
import com.adminservice.doctor.model.Doctor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.UUID;

public class DoctorSpecification {

    public static Specification<Doctor> getDoctorSpecification(String category, String value) {
        return (Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            if (value == null || value.trim().isEmpty()) {
                return cb.conjunction(); // return all records if value is empty
            }

            switch (category.toLowerCase()) {
                case "name":
                    return cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");

                case "gender":
                    try {
                        Gender genderEnum = Gender.valueOf(value.toUpperCase());
                        return cb.equal(root.get("gender"), genderEnum);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // invalid gender, return no results
                    }

                case "specialization":
                    return cb.like(cb.lower(root.get("specialization")), "%" + value.toLowerCase() + "%");

                case "qualification":
                    return cb.like(cb.lower(root.get("qualification")), "%" + value.toLowerCase() + "%");

                case "license_number":
                    return cb.equal(root.get("licenseNumber"), value);

                case "affiliated_hospital":
                    return cb.like(cb.lower(root.get("affiliatedHospital")), "%" + value.toLowerCase() + "%");

                case "contact_email":
                    return cb.like(cb.lower(root.get("contactEmail")), "%" + value.toLowerCase() + "%");

                case "contact_phone":
                    return cb.equal(root.get("contactPhone"), value);

                case "practice_location":
                    return cb.like(cb.lower(root.get("practiceLocation")), "%" + value.toLowerCase() + "%");

                case "role_code":
                    return cb.equal(root.get("roleCode"), value);

                case "doctor_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("doctorId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // invalid UUID
                    }

                default:
                    return cb.conjunction(); // no filter if unknown category
            }
        };
    }
}
