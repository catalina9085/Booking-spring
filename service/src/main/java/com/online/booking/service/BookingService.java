package com.online.booking.service;



import com.online.booking.dtos.BookingRequest;
import com.online.booking.entities.Booking;
import com.online.booking.entities.NamedGroup;
import com.online.booking.entities.Room;
import com.online.booking.entities.User;
import com.online.booking.repos.BookingRepo;
import com.online.booking.repos.GroupRepo;
import com.online.booking.repos.RoomRepo;
import com.online.booking.repos.UserRepo;
import com.online.booking.vos.BookingVO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {
    private GroupRepo groupRepo;
    private UserRepo userRepo;
    private RoomRepo roomRepo;
    private BookingRepo bookingRepo;

    public BookingService(GroupRepo groupRepo, UserRepo userRepo, RoomRepo roomRepo, BookingRepo bookingRepo) {
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
        this.bookingRepo = bookingRepo;
    }

    public List<NamedGroup> getGroups(){
        return this.groupRepo.findAll();
    }

    public void saveBooking(Principal principal, BookingRequest request) {
        User user=userRepo.findByEmail(principal.getName()).get();
        Room room=roomRepo.findById(request.getRoomId()).get();
        NamedGroup group;
        if(groupRepo.existsByName(request.getNamedGroup()))group=groupRepo.findByName(request.getNamedGroup()).get();
        else{
            group=new NamedGroup();
            group.setName(request.getNamedGroup());
            group.setSize(request.getSize());
            groupRepo.save(group);
        }
        if(group.getSize()>room.getCapacity()) throw new RuntimeException("The group size exceeds the room capacity!");
        validateBookingTimeRange(request.getDate(),request.getStartTime(),request.getEndTime());
        checkAvailability(room,request);

        Booking booking = new Booking();
        booking.setDate(request.getDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setRoom(room);
        booking.setGroup(group);
        booking.setUser(user);
        bookingRepo.save(booking);
    }

    private void checkAvailability(Room room, BookingRequest request) {
        LocalDate requestedDate = request.getDate();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        List<Booking> existingBookings = room.getBookings();

        boolean hasConflict = existingBookings.stream()
                .filter(b -> b.getDate().isEqual(requestedDate) && !b.getRoom().getId().equals(request.getRoomId()))
                .anyMatch(b -> {
                    boolean overlap = isOverlapping(b.getStartTime(), b.getEndTime(), startTime, endTime);
                    return overlap;
                });

        if (hasConflict)
            throw new RuntimeException("This room is already booked in the selected interval.");
    }

    private boolean isOverlapping(String start1, String end1, String start2, String end2) {
        LocalTime s1 = LocalTime.parse(start1);
        LocalTime e1 = LocalTime.parse(end1);
        LocalTime s2 = LocalTime.parse(start2);
        LocalTime e2 = LocalTime.parse(end2);

        return s1.isBefore(e2) && e1.isAfter(s2);
    }

    private void validateBookingTimeRange(LocalDate date, String startTime, String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        LocalTime earliest = LocalTime.of(8, 0);
        LocalTime latest = LocalTime.of(18, 0);

        if (!start.isBefore(end)) {
            throw new RuntimeException("Start time must be before end time.");
        }

        if (start.isBefore(earliest) || end.isAfter(latest)) {
            throw new RuntimeException("Bookings must be between 08:00 and 18:00.");
        }

        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            throw new RuntimeException("Cannot book for a past date.");
        }

        if (date.isEqual(today) && start.isBefore(LocalTime.now())) {
            throw new RuntimeException("Cannot book for a time in the past.");
        }
    }

    public List<BookingVO> getAllBookings(Principal principal){
        User user=userRepo.findByEmail(principal.getName()).get();
        return bookingRepo.findAllByUserId(user.getId());
    }

    public void cancelBooking(Long bookingId){
        Booking booking=bookingRepo.findById(bookingId).get();
        LocalDate date = booking.getDate();
        String startTime = booking.getStartTime();
        LocalDateTime bookingDateTime = LocalDateTime.of(date,LocalTime.parse(startTime));

        LocalDateTime now = LocalDateTime.now();

        if (Duration.between(now, bookingDateTime).toHours() >= 24) {
            bookingRepo.deleteById(bookingId);
        } else {
            throw new RuntimeException("Couldn't cancel the booking.");
        }
    }


    public ByteArrayResource getBookingsAsCSV(Principal principal){
        User user=userRepo.findByEmail(principal.getName()).get();
        List<Booking> bookings=user.getBookings();

        StringBuilder csv = new StringBuilder();

        csv.append("Room,Location,Date\n");
        for (Booking b : bookings) {
            csv.append(b.getRoom().getName()).append(",")
                    .append(b.getRoom().getLocation().toString()).append(",")
                    .append(b.getDate()).append("\n");
        }

        byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        ByteArrayResource resource = new ByteArrayResource(bytes);
        return resource;
    }

    public BookingVO getBookingById(Long id){
        return bookingRepo.findVOById(id).get();
    }

    public void editBooking(Principal principal, BookingRequest request,Long bookingId) {
        User user=userRepo.findByEmail(principal.getName()).get();
        Booking booking=bookingRepo.findById(bookingId).get();
        Room room=roomRepo.findById(request.getRoomId()).get();
        validateBookingTimeRange(request.getDate(),request.getStartTime(),request.getEndTime());
        checkAvailability(room,request);
        NamedGroup group;
        if(groupRepo.existsByName(request.getNamedGroup())) group=groupRepo.findByName(request.getNamedGroup()).get();
        else{
            group=new NamedGroup();
            group.setName(request.getNamedGroup());
            group.setSize(request.getSize());
            groupRepo.save(group);
        }
        if(group.getSize()>request.getSize()) throw new RuntimeException("The group size exceeds the room capacity!");
        booking.setDate(request.getDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setRoom(room);
        booking.setUser(user);
        booking.setGroup(group);
        bookingRepo.save(booking);

    }
}
