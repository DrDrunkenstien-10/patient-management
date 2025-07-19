package com.adminservice.receptionist.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.adminservice.receptionist.enums.Gender;
import com.adminservice.receptionist.model.Receptionist;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ReceptionistSpecification {
    
     public static Specification<Receptionist> getReceptionistSpecification(String category, String value) {
        return (Root<Receptionist> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

                case "date_of_birth":
                    return cb.like(cb.lower(root.get("dateOfBirth")), "%" + value.toLowerCase() + "%");

                case "employee_code":
                    return cb.like(cb.lower(root.get("employeeCode")), "%" + value.toLowerCase() + "%");

                case "department":
                    return cb.equal(root.get("department"), value);

                case "contact_email":
                    return cb.like(cb.lower(root.get("contactEmail")), "%" + value.toLowerCase() + "%");

                case "contact_phone":
                    return cb.equal(root.get("contactPhone"), value);

                case "assigned_facility":
                    return cb.like(cb.lower(root.get("assignedFacility")), "%" + value.toLowerCase() + "%");

                case "role_title":
                    return cb.equal(root.get("role_title"), value);

                case "receptionist_id":
                    try {
                        UUID id = UUID.fromString(value);
                        return cb.equal(root.get("receptionistId"), id);
                    } catch (IllegalArgumentException ex) {
                        return cb.disjunction(); // invalid UUID
                    }

                default:
                    return cb.conjunction(); // no filter if unknown category
            }
        };
    }

}
