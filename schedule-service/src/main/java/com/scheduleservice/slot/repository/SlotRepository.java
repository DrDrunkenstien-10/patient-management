package com.scheduleservice.slot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scheduleservice.slot.model.Slot;

public interface SlotRepository extends JpaRepository<Slot, UUID>  {
    
}
