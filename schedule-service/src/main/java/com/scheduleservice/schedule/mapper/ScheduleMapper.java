package com.scheduleservice.schedule.mapper;

import com.scheduleservice.schedule.dto.ScheduleRequestDTO;
import com.scheduleservice.schedule.dto.ScheduleResponseDTO;
import com.scheduleservice.schedule.model.Schedule;

public class ScheduleMapper {

    public static ScheduleResponseDTO toDto(Schedule schedule) {
        if (schedule == null) {
            return null;
        }
        ScheduleResponseDTO dto = new ScheduleResponseDTO();
        dto.setScheduleId(schedule.getScheduleId().toString());
        dto.setDocId(schedule.getDocId().toString());
        dto.setScheduleType(schedule.getScheduleType().name());
        dto.setStartDate(schedule.getStartDate().toString());
        dto.setEndDate(schedule.getEndDate().toString());
        
        return dto;
    }
    public static Schedule toModel(ScheduleRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setScheduleId(dto.getScheduleId());
        schedule.setDocId(dto.getDocId());  
        schedule.setScheduleType(dto.getScheduleType());
        schedule.setStartDate(dto.getStartDate());
        schedule.setEndDate(dto.getEndDate());
        return schedule;
    }
}