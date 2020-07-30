package com.egorhristoforov.eventikrestapi.controllers;

import com.egorhristoforov.eventikrestapi.dtos.requests.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.EventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.*;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping(value = "")
    public ResponseEntity<EventCreateResponse> createEvent(@Valid @RequestBody EventCreateRequest body)
            throws UnauthorizedException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(body));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<EventsListResponse>> getEvents(@RequestParam(value = "city-id") @Positive Long cityId)
            throws ResourceNotFoundException{
        return ResponseEntity.ok(eventService.getEventsList(cityId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EventRetrieveResponse> getEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, ForbiddenException {
        eventService.deleteEvent(eventId);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EventUpdateResponse> updateEvent(@PathVariable(value = "id") @Positive Long eventId,
                                                           @Valid @RequestBody EventUpdateRequest body)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        return ResponseEntity.ok(eventService.updateEvent(eventId, body));
    }

    @PostMapping(value = "/{id}/booking")
    public ResponseEntity<EventBookingCreateResponse> bookEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException, UnauthorizedException, BadRequestException {
        return ResponseEntity.ok(eventService.bookEvent(eventId));
    }

    @DeleteMapping(value = "/{id}/booking")
    public ResponseEntity<EventBookingDeleteResponse> deleteBookOnEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, BadRequestException {
        return ResponseEntity.ok(eventService.deleteBookingOnEvent(eventId));
    }

    @GetMapping(value = "/{id}/visitors")
    public ResponseEntity<List<EventVisitorsListResponse>> getVisitors(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException, BadRequestException {
        return ResponseEntity.ok(eventService.getVisitorsListForEvent(eventId));
    }
}
