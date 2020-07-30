package com.egorhristoforov.eventikrestapi.services.impl;

import com.egorhristoforov.eventikrestapi.dtos.requests.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.EventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.*;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.models.*;
import com.egorhristoforov.eventikrestapi.repositories.*;
import com.egorhristoforov.eventikrestapi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    BookingRepository bookingRepository;

    private boolean hasRole(User user, String roleName) {
        Optional<UserRole> role = roleRepository.findByName(roleName);

        return role.isPresent() && user.getRoles().contains(role.get());
    }

    public EventCreateResponse createEvent(EventCreateRequest request)
            throws UnauthorizedException, ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        City eventCity = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("Provided city not found"));

        Event createdEvent = new Event();
        createdEvent.setCity(eventCity);
        createdEvent.setApartment(request.getApartment());
        createdEvent.setTitle(request.getTitle());
        createdEvent.setDescription(request.getDescription());
        createdEvent.setDate(request.getDate());
        createdEvent.setCreatedDate(new Date());
        createdEvent.setModifiedDate(new Date());
        createdEvent.setLongitude(request.getLongitude());
        createdEvent.setLatitude(request.getLatitude());
        createdEvent.setRegistrationRequired(request.isRegistrationRequired());
        createdEvent.setOwner(currentUser);

        eventRepository.save(createdEvent);

        EventCreateResponse response = new EventCreateResponse();
        response.setId(createdEvent.getId());
        response.setLatitude(createdEvent.getLatitude());
        response.setLongitude(createdEvent.getLongitude());
        response.setApartment(createdEvent.getApartment());
        response.setTitle(createdEvent.getTitle());
        response.setDescription(createdEvent.getDescription());
        response.setDate(createdEvent.getDate());
        response.setCountOfVisitors(0);
        response.setRegistrationRequired(createdEvent.isRegistrationRequired());
        response.setLastModifiedDate(createdEvent.getModifiedDate());

        return response;
    }

    @Transactional
    public List<EventsListResponse> getEventsList(Long cityId) throws ResourceNotFoundException {
        City eventCity = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("Provided city not found"));

        return eventCity.getEvents()
                .stream()
                .filter(event -> event.getDate().after(new Date(System.currentTimeMillis() - 3600 * 1000)))
                .sorted(Comparator.comparing(Event::getId).reversed())
                .map(event -> new EventsListResponse(event.getId(), event.getLongitude(), event.getLatitude(),
                        event.getApartment(), event.getTitle(), event.getDate(), event.getModifiedDate()))
                .collect(Collectors.toList());
    }

    public EventRetrieveResponse getEvent(Long eventId) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        EventRetrieveResponse response = new EventRetrieveResponse();
        response.setId(event.getId());
        response.setLongitude(event.getLongitude());
        response.setLatitude(event.getLatitude());
        response.setApartment(event.getApartment());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setDate(event.getDate());
        response.setRegistrationRequired(event.isRegistrationRequired());
        response.setLastModifiedDate(event.getModifiedDate());
        response.setCountOfVisitors(eventRepository.countOfVisitors(eventId));

        return response;
    }

    public void deleteEvent(Long eventId) throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (!event.getOwner().getId().equals(currentUser.getId()) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Access denied");
        }

        eventRepository.delete(event);
    }

    public EventUpdateResponse updateEvent(Long eventId, EventUpdateRequest request)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (!event.getOwner().getId().equals(currentUser.getId()) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Access denied");
        }

        event.setLongitude(request.getLongitude() == null ? event.getLongitude() : request.getLongitude());
        event.setLatitude(request.getLatitude() == null ? event.getLatitude() : request.getLatitude());
        event.setApartment(request.getApartment() == null ? event.getApartment() : request.getApartment());
        event.setTitle(request.getTitle() == null ? event.getTitle() : request.getTitle());
        event.setDescription(request.getDescription() == null ? event.getDescription() : request.getDescription());
        event.setDate(request.getDate() == null ? event.getDate() : request.getDate());
        event.setRegistrationRequired(request.isRegistrationRequired() == null ? event.isRegistrationRequired() : request.isRegistrationRequired());

        event.setModifiedDate(new Date());

        eventRepository.save(event);

        EventUpdateResponse response = new EventUpdateResponse();
        response.setId(event.getId());
        response.setLongitude(event.getLongitude());
        response.setLatitude(event.getLatitude());
        response.setApartment(event.getApartment());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setDate(event.getDate());
        response.setCountOfVisitors(eventRepository.countOfVisitors(eventId));
        response.setRegistrationRequired(event.isRegistrationRequired());
        response.setLastModifiedDate(event.getModifiedDate());

        return response;
    }

    public EventBookingCreateResponse bookEvent(Long eventId)
            throws ResourceNotFoundException, UnauthorizedException, BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (!event.isRegistrationRequired()) {
            throw new BadRequestException("Registration not required");
        }

        if (event.getOwner().equals(currentUser)) {
            throw new BadRequestException("User is owner of event");
        }

        EventBookingCreateResponse response = new EventBookingCreateResponse();
        response.setId(event.getId());
        response.setLongitude(event.getLongitude());
        response.setLatitude(event.getLatitude());
        response.setApartment(event.getApartment());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setDate(event.getDate());
        response.setCountOfVisitors(eventRepository.countOfVisitors(eventId));
        response.setRegistrationRequired(event.isRegistrationRequired());
        response.setLastModifiedDate(event.getModifiedDate());

        if (bookingRepository.existsByEventAndVisitor(currentUser.getId(), eventId)) {
            return response;
        }

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setVisitor(currentUser);
        booking.setBookingDate(new Date());

        bookingRepository.save(booking);

        response.setCountOfVisitors(response.getCountOfVisitors() + 1);

        return response;
    }

    public EventBookingDeleteResponse deleteBookingOnEvent(Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (!event.isRegistrationRequired()) {
            throw new BadRequestException("Registration not required");
        }

        Optional<Booking> booking = bookingRepository.findByEventAndVisitor(currentUser.getId(), eventId);

        EventBookingDeleteResponse response = new EventBookingDeleteResponse();
        response.setId(event.getId());
        response.setLongitude(event.getLongitude());
        response.setLatitude(event.getLatitude());
        response.setApartment(event.getApartment());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setDate(event.getDate());
        response.setCountOfVisitors(eventRepository.countOfVisitors(eventId));
        response.setRegistrationRequired(event.isRegistrationRequired());
        response.setLastModifiedDate(event.getModifiedDate());

        if (!booking.isPresent()) {
            return response;
        }

        bookingRepository.delete(booking.get());

        response.setCountOfVisitors(response.getCountOfVisitors() - 1);

        return response;
    }

    public List<EventVisitorsListResponse> getVisitorsListForEvent(Long eventId)
            throws UnauthorizedException, ForbiddenException, ResourceNotFoundException, BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (!event.isRegistrationRequired()) {
            throw new BadRequestException("Registration not required");
        }

        if (!event.getOwner().equals(currentUser) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Access denied");
        }

        return event.getBookings().stream()
                .sorted(Comparator.comparing(Booking::getBookingDate))
                .map(Booking::getVisitor)
                .map(visitor -> new EventVisitorsListResponse(visitor.getName(), visitor.getSurname()))
                .collect(Collectors.toList());
    }
}
