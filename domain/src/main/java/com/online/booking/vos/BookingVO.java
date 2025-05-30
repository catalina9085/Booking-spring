package com.online.booking.vos;

import com.online.booking.entities.Location;
import com.online.booking.entities.NamedGroup;

import javax.swing.*;
import java.time.LocalDate;

public interface BookingVO {
    Long getId();
    LocalDate getDate();
    String getStartTime();
    String getEndTime();
    NamedGroup getGroup();
    String getRoomName();
    Long getRoomId();
    String getUserEmail();
    LocationProjection getRoomLocation();

    interface LocationProjection {
        String getCity();
        String getBuilding();
        String getStreet();
        Double getLat();
        Double getLng();
    }
}
