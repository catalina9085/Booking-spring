package com.online.booking.controllers;

import com.online.booking.dtos.Filter;
import com.online.booking.entities.Booking;
import com.online.booking.entities.Room;
import com.online.booking.service.PublicRoomService;
import com.online.booking.vos.BookingVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user/rooms")
public class PublicRoomController {
    private PublicRoomService roomService;

    public PublicRoomController(PublicRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(this.roomService.getAllRooms());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @PostMapping("/filtered")
    public ResponseEntity<List<Room>> getFilteredRooms(@RequestBody Filter filter) {
        return ResponseEntity.ok(roomService.getFilteredRooms(filter));
    }

    @GetMapping("/filtered/{date}")
    public ResponseEntity<List<Room>> getRoomsFilteredByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(roomService.getRoomsFilteredByDate(date));
    }

    @GetMapping("/bookings/{roomId}")
    public ResponseEntity<List<Booking>> getBookings(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getBookings(roomId));
    }

    @GetMapping("/amenities")
    public ResponseEntity<List<String>> getAmenities() {
        return ResponseEntity.ok(roomService.getAmenities());
    }

}
