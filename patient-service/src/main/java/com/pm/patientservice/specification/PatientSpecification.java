package com.pm.patientservice.specification;

import org.springframework.data.jpa.domain.Specification;

import com.pm.patientservice.enums.Gender;
import com.pm.patientservice.model.Patient;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PatientSpecification {

    public static Specification<Patient> getPatientSpecification(String category, String value) {
        return (Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            if (value == null || value.trim().isEmpty()) {
                return cb.conjunction(); // Return all records if value is empty
            }

            switch (category.toLowerCase()) {
                case "name":
                    return cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");

                case "gender":
                    try {
                        Gender genderEnum = Gender.valueOf(value.toUpperCase());
                        return cb.equal(root.get("gender"), genderEnum);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // Invalid gender, return no results
                    }

                case "date_of_birth":
                    return cb.equal(root.get("dateOfBirth").as(String.class), value);

                case "aadhaar_number":
                    return cb.equal(root.get("aadhaarNumber"), value);

                case "contact_phone":
                    return cb.equal(root.get("contactPhone"), value);

                case "email":
                    return cb.like(cb.lower(root.get("email")), "%" + value.toLowerCase() + "%");

                case "address":
                    return cb.like(cb.lower(root.get("address")), "%" + value.toLowerCase() + "%");

                case "medical_history":
                    return cb.like(cb.lower(root.get("medicalHistory")), "%" + value.toLowerCase() + "%");

                case "allergies":
                    return cb.like(cb.lower(root.get("allergies")), "%" + value.toLowerCase() + "%");

                case "medications":
                    return cb.like(cb.lower(root.get("medications")), "%" + value.toLowerCase() + "%");

                case "consents":
                    return cb.like(cb.lower(root.get("consents")), "%" + value.toLowerCase() + "%");

                case "emergency_contact":
                    return cb.like(cb.lower(root.get("emergencyContact")), "%" + value.toLowerCase() + "%");

                case "created_at":
                    return cb.equal(root.get("createdAt"), value);

                case "updated_at":
                    return cb.equal(root.get("updatedAt"), value);

                default:
                    return cb.conjunction(); // No filter if unknown category
            }
        };
    }
}

