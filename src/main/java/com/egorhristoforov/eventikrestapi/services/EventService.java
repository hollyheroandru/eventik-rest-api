package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.*;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;

import java.util.List;

public interface EventService {
    EventCreateResponse createEvent(EventCreateRequest request)
            throws UnauthorizedException, ResourceNotFoundException;

    List<EventsListResponse> getEventsList(Long cityId)
            throws ResourceNotFoundException;

    EventRetrieveResponse getEvent(Long eventId)
            throws ResourceNotFoundException;

    void deleteEvent(Long eventId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException;

    EventUpdateResponse updateEvent(Long eventId, EventUpdateRequest request)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException;

    EventBookingCreateResponse bookEvent(Long eventId)
            throws ResourceNotFoundException, UnauthorizedException, BadRequestException;

    EventBookingDeleteResponse deleteBookingOnEvent(Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, BadRequestException;

    List<EventVisitorsListResponse> getVisitorsListForEvent(Long eventId)
            throws UnauthorizedException, ForbiddenException, ResourceNotFoundException, BadRequestException;
}
