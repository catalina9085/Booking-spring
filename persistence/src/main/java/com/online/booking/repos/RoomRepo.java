package com.online.booking.repos;

import com.online.booking.entities.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    boolean existsByName(String name);
    List<Room> findAll(Sort sort);
}
