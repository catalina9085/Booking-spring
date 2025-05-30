package com.online.booking.controllers;



import com.online.booking.dtos.RoomDTO;
import com.online.booking.entities.NamedGroup;
import com.online.booking.service.AdminService;
import com.online.booking.vos.BookingVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/rooms")
    public ResponseEntity<String> addRoom(@RequestBody RoomDTO roomDTO) {
        try{
            this.adminService.addRoom(roomDTO);
            return ResponseEntity.ok("Room Added");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/rooms")
    public ResponseEntity<String> editRoom(@RequestBody RoomDTO roomDTO) {
        this.adminService.editRoom(roomDTO);
        return ResponseEntity.ok("Room Edited");
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        adminService.deleteRoom(roomId);
        return ResponseEntity.ok("Room Deleted");
    }


    @PostMapping("/groups")
    public ResponseEntity<String> addGroups(@RequestPart("file") MultipartFile file) {
        try{
            adminService.addGroups(file);
            return ResponseEntity.ok("Groups added");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/groups")
    public ResponseEntity<List<NamedGroup>> getGroups() {
        return ResponseEntity.ok(adminService.getGroups());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingVO>> getAllBookings() {
        return ResponseEntity.ok(adminService.getAllBookings());
    }

}
