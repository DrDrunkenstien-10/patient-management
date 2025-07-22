package com.scheduleservice.availability.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.scheduleservice.schedule.model.Schedule;
import com.scheduleservice.slot.model.Slot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;

@Entity
@Table(name = "availability", schema = "schedule")
public class Availability {

    @Id
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "doc_id")
    private UUID docId;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy by default to avoid loading Slot unless needed
    @JoinColumn(name = "slot_id", referencedColumnName = "slot_id", foreignKey = @ForeignKey(name = "fk_availability_slot"))
    private Slot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", foreignKey = @ForeignKey(name = "fk_availability_schedule"))
    private Schedule schedule;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "unavailability_reason")
    private String unavailabilityReason;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDocId() {
        return docId;
    }

    public void setDocId(UUID docId) {
        this.docId = docId;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getUnavailabilityReason() {
        return unavailabilityReason;
    }

    public void setUnavailabilityReason(String unavailabilityReason) {
        this.unavailabilityReason = unavailabilityReason;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}