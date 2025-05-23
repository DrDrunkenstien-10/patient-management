package com.scheduleservice.availability.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scheduleservice.availability.model.Availability;

public interface AvailabiltyRepository extends JpaRepository<Availability, UUID> {

}