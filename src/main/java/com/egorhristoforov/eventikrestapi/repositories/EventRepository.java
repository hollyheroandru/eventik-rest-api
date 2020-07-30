package com.egorhristoforov.eventikrestapi.repositories;

import com.egorhristoforov.eventikrestapi.models.Event;
import com.egorhristoforov.eventikrestapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT COUNT(e) > 0 FROM events e WHERE e.owner_id = :userId AND e.id = :eventId", nativeQuery = true)
    boolean existsByEventAndOwner(@Param(value = "eventId") Long eventId,
                                  @Param(value = "userId") Long userId);

    @Query(value = "SELECT COUNT(b) FROM bookings b WHERE b.event_id = :eventId", nativeQuery = true)
    int countOfVisitors(@Param(value = "eventId") Long eventId);
}
