package com.online.booking.service;


import com.online.booking.dtos.Filter;
import com.online.booking.entities.Booking;
import com.online.booking.entities.Room;
import com.online.booking.repos.AmenityRepo;
import com.online.booking.repos.RoomRepo;
import com.online.booking.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class PublicRoomService {
    private RoomRepo roomRepo;
    private UserRepo userRepo;
    private AmenityRepo amenityRepo;

    public PublicRoomService(RoomRepo roomRepo,UserRepo userRepo, AmenityRepo amenityRepo) {
        this.roomRepo = roomRepo;
        this.userRepo=userRepo;
        this.amenityRepo=amenityRepo;
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepo.findAll();
        setAvailability(rooms,LocalDate.now());
        return rooms;
    }

    public List<Room> getFilteredRooms(Filter filter) {
        List<Room> filteredRooms = roomRepo.findAll();

        if (filter.getAmenities() != null){
            filteredRooms = filteredRooms.stream()
                    .filter(room -> room.getAmenities().containsAll(filter.getAmenities()))
                    .toList();
        }

        if(filter.getDate()!=null)
            setAvailability(filteredRooms,filter.getDate());

        if(filter.getAvailable()!=null){
                filteredRooms = filteredRooms.stream()
                        .filter(room->filter.getAvailable() ? room.getAvailable() : !room.getAvailable())
                        .toList();
        }
        return filteredRooms;
    }


    public void setAvailability(List<Room> rooms, LocalDate date) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime programStart = LocalTime.of(8, 0);   // 08:00
        LocalTime programEnd = LocalTime.of(18, 0);    // 18:00
        int minimumFreeMinutes = 120; // 2 ore

        for (Room room : rooms) {
            List<Booking> bookingsForDay = room.getBookings().stream()
                    .filter(b -> b.getDate().isEqual(date))
                    .sorted(Comparator.comparing(b -> LocalTime.parse(b.getStartTime(), timeFormatter)))
                    .toList();

            boolean available = false;
            LocalTime current = programStart;

            for (Booking booking : bookingsForDay) {
                LocalTime bookingStart = LocalTime.parse(booking.getStartTime(), timeFormatter);
                LocalTime bookingEnd = LocalTime.parse(booking.getEndTime(), timeFormatter);

                if (current.until(bookingStart, java.time.temporal.ChronoUnit.MINUTES) >= minimumFreeMinutes) {
                    available = true;
                    break;
                }
                if (bookingEnd.isAfter(current)) {
                    current = bookingEnd;
                }
            }

            if (!available && current.until(programEnd, java.time.temporal.ChronoUnit.MINUTES) >= minimumFreeMinutes) {
                available = true;
            }

            room.setAvailable(available);
            roomRepo.save(room);
        }
    }

    public List<Room> getRoomsFilteredByDate(LocalDate date) {
        List<Room> rooms = roomRepo.findAll();
        setAvailability(rooms,date);
        return rooms;
    }

    public List<Booking> getBookings(Long roomId){
        Room room = roomRepo.findById(roomId).get();
        return room.getBookings();
    }

    public Room getRoomById(Long id){
        return this.roomRepo.findById(id).get();
    }

    public List<String> getAmenities(){
        return this.amenityRepo.findAll().stream().map(a->a.getName()).toList();
    }

}
