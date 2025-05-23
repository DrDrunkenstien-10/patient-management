package com.scheduleservice.schedule.dto;

public class ScheduleResponseDTO {

    private String ScheduleId;
    private String docId;
    private String scheduleType;
    private String startDate;
    private String endDate;


    public String getScheduleId() {
        return ScheduleId;
    }
    public void setScheduleId(String scheduleId) {
        ScheduleId = scheduleId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}