package com.scheduleservice.slot.repository;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.scheduleservice.slot.model.Slot;

public interface SlotRepository extends JpaRepository<Slot, UUID>, JpaSpecificationExecutor<Slot> {
    boolean existsByDoctorIdAndName(UUID doctorId, String name);

    boolean existsByDoctorIdAndStartTimeAndEndTime(UUID doctorId, LocalTime startTime, LocalTime endTime);
}
