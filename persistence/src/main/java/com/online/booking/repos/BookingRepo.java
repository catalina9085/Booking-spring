package com.online.booking.repos;


import com.online.booking.entities.Booking;
import com.online.booking.vos.BookingVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<BookingVO> findAllBy();

    @Query("""
select b.id as id, 
       b.date as date, 
       b.startTime as startTime,
       b.endTime as endTime, 
       b.group as group,
       b.user.email as userEmail,
       b.room.name as roomName,
       b.room.id as roomId,
       b.room.location as roomLocation
from Booking b
where b.user.id = :userId
""")
    List<BookingVO> findAllByUserId(@Param("userId") Long userId);

    @Query("""
select b.id as id, 
       b.date as date, 
       b.startTime as startTime,
       b.endTime as endTime, 
       b.group as group,
       b.user.email as userEmail,
       b.room.name as roomName,
       b.room.id as roomId,
       b.room.location as roomLocation
from Booking b
where b.id = :id
""")
    Optional<BookingVO> findVOById(@Param("id") Long id);
}
