package com.online.booking.service;


import com.online.booking.dtos.RoomDTO;
import com.online.booking.entities.Amenity;
import com.online.booking.entities.Location;
import com.online.booking.entities.NamedGroup;
import com.online.booking.entities.Room;
import com.online.booking.repos.*;
import com.online.booking.vos.BookingVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private RoomRepo roomRepo;
    private GroupRepo groupRepo;
    private BookingRepo bookingRepo;
    private LocationRepo locationRepo;
    private AmenityRepo amenityRepo;

    public AdminService(RoomRepo roomRepo, GroupRepo groupRepo,BookingRepo bookingRepo,LocationRepo locationRepo,AmenityRepo amenityRepo) {
        this.roomRepo = roomRepo;
        this.groupRepo = groupRepo;
        this.bookingRepo=bookingRepo;
        this.locationRepo=locationRepo;
        this.amenityRepo=amenityRepo;
    }

    public void addRoom(RoomDTO room){
        if(roomRepo.existsByName(room.getName()))
            throw new RuntimeException("A room with this name already exists. Please choose a different name.");

        Room newRoom=new Room();
        newRoom.setName(room.getName());
        newRoom.setCapacity(room.getCapacity());

        Location location=locationRepo.findByLatAndLng(room.getLocation().getLat(),room.getLocation().getLng()).orElse(room.getLocation());
        locationRepo.save(location);
        newRoom.setLocation(location);

        Set<Amenity> amenitySet = room.getAmenities().stream()
                .map(name -> amenityRepo.findByName(name)
                        .orElseGet(() -> {
                            Amenity a = new Amenity();
                            a.setName(name);
                            return amenityRepo.save(a);
                        })
                ).collect(Collectors.toSet());

        newRoom.setAmenityEntities(amenitySet);
        roomRepo.save(newRoom);
    }

    public void editRoom(RoomDTO roomDTO){
        Room room=roomRepo.findById(roomDTO.getId()).get();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setLocation(locationRepo.findByLatAndLng(roomDTO.getLocation().getLat(),roomDTO.getLocation().getLng()).orElse(roomDTO.getLocation()));
            for(String a:roomDTO.getAmenities()){
            System.out.println("Amenity: "+a+"\n");
        }
        Set<Amenity> amenitySet = roomDTO.getAmenities().stream()
                .map(name -> amenityRepo.findByName(name)
                        .orElseGet(() -> {
                            Amenity a = new Amenity();
                            a.setName(name);
                            return amenityRepo.save(a);
                        })
                ).collect(Collectors.toSet());
        room.setAmenityEntities(amenitySet);
        for(Amenity a:amenitySet){
            System.out.println("Amenity: "+a.getName()+"\n");
        }
        this.roomRepo.save(room);
    }

    public void deleteRoom(Long roomId){
        roomRepo.deleteById(roomId);
    }

    public List<NamedGroup> getGroups(){
        return this.groupRepo.findAll();
    }

    public void addGroups(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 2) continue;
                NamedGroup group;
                if(groupRepo.existsByName(values[0])) group=groupRepo.findByName(values[0]).get();
                else group=new NamedGroup();
                group.setName(values[0]);
                group.setSize(Integer.parseInt(values[1]));
                this.groupRepo.save(group);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file");
        }

    }

    public List<BookingVO> getAllBookings(){
        return this.bookingRepo.findAllBy();
    }
}
