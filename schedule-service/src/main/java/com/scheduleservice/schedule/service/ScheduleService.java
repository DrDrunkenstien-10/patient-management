package com.scheduleservice.schedule.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scheduleservice.schedule.dto.ScheduleRequestDTO;
import com.scheduleservice.schedule.dto.ScheduleResponseDTO;
import com.scheduleservice.schedule.repository.ScheduleRepository;

import jakarta.transaction.Transactional;

import com.scheduleservice.schedule.exception.ScheduleExeptionHandler;

import com.scheduleservice.schedule.mapper.ScheduleMapper;
import com.scheduleservice.schedule.model.Schedule;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;

    }

    public List<ScheduleResponseDTO> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        List<ScheduleResponseDTO> scheduleResponseDTOs = schedules.stream()
                .map(schedule -> ScheduleMapper.toDto(schedule))
                .toList();

        return scheduleResponseDTOs;
    }

    public ScheduleResponseDTO getScheduleById(UUID scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleExeptionHandler(
                        "Schedule not found with ID: " + scheduleId));
        return ScheduleMapper.toDto(schedule);
    }

    public ScheduleResponseDTO updateSchedule(UUID scheduleId, ScheduleRequestDTO scheduleRequestDTO) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleExeptionHandler(
                        "Schedule not found with ID: " + scheduleId));

        schedule.setScheduleType(scheduleRequestDTO.getScheduleType());
        schedule.setStartDate(scheduleRequestDTO.getStartDate());
        schedule.setEndDate(scheduleRequestDTO.getEndDate());

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return ScheduleMapper.toDto(updatedSchedule);
    }

    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleExeptionHandler(
                        "Schedule not found with ID: " + scheduleId));
        scheduleRepository.delete(schedule);
    }

}
