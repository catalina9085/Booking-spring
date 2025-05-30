package com.online.booking.dtos;

import java.time.LocalDate;
import java.util.List;

public class Filter {
    private List<String> amenities;
    private Boolean available;
    private LocalDate date;

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
