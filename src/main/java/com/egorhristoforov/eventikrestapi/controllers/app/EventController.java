package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.*;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@Validated
@Api(value = "event-controller", tags = "/events")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create event", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<EventCreateResponse> createEvent(@Valid @RequestBody EventCreateRequest body)
            throws UnauthorizedException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(body));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get events list")
    public ResponseEntity<List<EventsListResponse>> getEvents(@RequestParam(value = "city-id") @Positive Long cityId)
            throws ResourceNotFoundException{
        return ResponseEntity.ok(eventService.getEventsList(cityId));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve event")
    public ResponseEntity<EventRetrieveResponse> getEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete event", authorizations = { @Authorization(value = "Access token") })
    public void deleteEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, ForbiddenException {
        eventService.deleteEvent(eventId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update event", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<EventUpdateResponse> updateEvent(@PathVariable(value = "id") @Positive Long eventId,
                                                           @Valid @RequestBody EventUpdateRequest body)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        return ResponseEntity.ok(eventService.updateEvent(eventId, body));
    }

    @PostMapping(value = "/{id}/booking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Book event by user", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<EventBookingCreateResponse> bookEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException, UnauthorizedException, BadRequestException {
        return ResponseEntity.ok(eventService.bookEvent(eventId));
    }

    @DeleteMapping(value = "/{id}/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete booking on event by user", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<EventBookingDeleteResponse> deleteBookOnEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, BadRequestException {
        return ResponseEntity.ok(eventService.deleteBookingOnEvent(eventId));
    }

    @GetMapping(value = "/{id}/visitors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get event visitors list", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<List<EventVisitorsListResponse>> getVisitors(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException, BadRequestException {
        return ResponseEntity.ok(eventService.getVisitorsListForEvent(eventId));
    }
}
