package com.egorhristoforov.eventikrestapi.repositories;

import com.egorhristoforov.eventikrestapi.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT COUNT(b) > 0 FROM bookings b WHERE b.visitor_id = :userId AND b.event_id = :eventId", nativeQuery = true)
    boolean existsByEventAndVisitor(@Param(value = "userId") Long userId,
                                    @Param(value = "eventId") Long eventId);

    @Query(value = "SELECT * FROM bookings b WHERE b.visitor_id = :userId AND b.event_id = :eventId", nativeQuery = true)
    Optional<Booking> findByEventAndVisitor(@Param(value = "userId") Long userId,
                                            @Param(value = "eventId") Long eventId);
}
