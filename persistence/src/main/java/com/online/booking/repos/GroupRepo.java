package com.online.booking.repos;

import com.online.booking.entities.NamedGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<NamedGroup, Long> {
    boolean existsByName(String name);
    Optional<NamedGroup> findByName(String name);
}
