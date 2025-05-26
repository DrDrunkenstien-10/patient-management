package com.scheduleservice.schedule.repository;

import java.time.LocalDate;

public interface ScheduleDateRange {
    LocalDate getStartDate();

    LocalDate getEndDate();
}
