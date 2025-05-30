package com.online.booking.repos;

import com.online.booking.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepo extends JpaRepository<Location,Long> {
    boolean existsByLatAndLng(double lat, double lng);
    Optional<Location> findByLatAndLng(double lat, double lng);
}
