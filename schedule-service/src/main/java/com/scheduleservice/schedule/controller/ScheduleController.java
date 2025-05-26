package com.scheduleservice.schedule.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;

import com.scheduleservice.schedule.dto.ScheduleRequestDTO;
import com.scheduleservice.schedule.dto.ScheduleResponseDTO;
import com.scheduleservice.schedule.service.ScheduleService;

import jakarta.validation.groups.Default;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getSchedules() {
        List<ScheduleResponseDTO> schedules = scheduleService.getSchedules();
        return ResponseEntity.ok().body(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable("id") UUID scheduleId) {
        ScheduleResponseDTO scheduleResponseDTO = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.ok().body(scheduleResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable("id") UUID scheduleId,
            @Validated({ Default.class }) @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        ScheduleResponseDTO scheduleResponseDTO = scheduleService.updateSchedule(scheduleId, scheduleRequestDTO);
        return ResponseEntity.ok().body(scheduleResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("id") UUID scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok("Schedule deleted successfully");
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        ScheduleResponseDTO scheduleResponseDTO = scheduleService.createSchedule(scheduleRequestDTO);
        return ResponseEntity.ok().body(scheduleResponseDTO);
    }
}
