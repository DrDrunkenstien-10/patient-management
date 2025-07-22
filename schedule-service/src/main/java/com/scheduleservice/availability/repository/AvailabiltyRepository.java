package com.scheduleservice.availability.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scheduleservice.availability.model.Availability;

public interface AvailabiltyRepository extends JpaRepository<Availability, UUID> {
    @Query("SELECT a.id FROM Availability a WHERE a.docId = :docId AND a.slot.slotId = :slotId AND a.date = :date")
    Optional<UUID> findIdByDocIdAndSlotIdAndDate(
            @Param("docId") UUID docId,
            @Param("slotId") UUID slotId,
            @Param("date") LocalDate date);
}