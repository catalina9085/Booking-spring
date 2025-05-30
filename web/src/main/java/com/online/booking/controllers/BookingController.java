package com.online.booking.controllers;


import com.online.booking.dtos.BookingRequest;
import com.online.booking.entities.Booking;
import com.online.booking.entities.NamedGroup;
import com.online.booking.service.BookingService;
import com.online.booking.vos.BookingVO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/groups")
    public ResponseEntity<List<NamedGroup>> getGroups() {
        return ResponseEntity.ok(bookingService.getGroups());
    }

    @PostMapping("/bookings")
    public ResponseEntity<String> saveBooking(Principal principal,@RequestBody BookingRequest request) {
        try{
            bookingService.saveBooking(principal, request);
            return ResponseEntity.ok("Room booked successfully");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/bookings/{bookingId}")
    public ResponseEntity<String> editBooking(Principal principal,@RequestBody BookingRequest request,@PathVariable Long bookingId) {
        try{
            bookingService.editBooking(principal, request,bookingId);
            return ResponseEntity.ok("Room booked successfully");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingVO>> getAllBookings(Principal principal) {
        return ResponseEntity.ok(bookingService.getAllBookings(principal));
    }
    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingVO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/bookings/csv")
    public ResponseEntity<Resource> getBookingsAsCSV(Principal principal) {
        ByteArrayResource resource = bookingService.getBookingsAsCSV(principal);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=bookings.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(resource.getByteArray().length)
                .body(resource);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok("Booking Deleted");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
