package com.online.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer capacity;
    private Boolean available;

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy="room")
    @JsonIgnore
    private List<Booking> bookings=new ArrayList<>();

//    @ElementCollection
//    private List<String> amenities=new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "room_amenities",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    @JsonIgnore
    private Set<Amenity> amenityEntities=new HashSet<>();

    @JsonProperty("amenities")
    public List<String> getAmenities() {
        return amenityEntities.stream()
                .map(Amenity::getName)
                .toList();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<Amenity> getAmenityEntities() {
        return amenityEntities;
    }

    public void setAmenityEntities(Set<Amenity> amenities) {
        this.amenityEntities = amenities;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
