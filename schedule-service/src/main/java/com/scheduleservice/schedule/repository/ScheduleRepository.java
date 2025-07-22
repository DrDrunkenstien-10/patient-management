package com.scheduleservice.schedule.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scheduleservice.schedule.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    boolean existsByDocId(UUID docId);

    List<Schedule> findByDocId(UUID docId);
}
