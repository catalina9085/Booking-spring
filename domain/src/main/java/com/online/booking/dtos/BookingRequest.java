package com.online.booking.dtos;

import java.time.LocalDate;

public class BookingRequest {
    private Long roomId;
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String namedGroup;
    private Integer size;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNamedGroup() {
        return namedGroup;
    }

    public void setNamedGroup(String namedGroup) {
        this.namedGroup = namedGroup;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
