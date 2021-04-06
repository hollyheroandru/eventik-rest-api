package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.chat.ChatMessage;
import com.egorhristoforov.eventikrestapi.dtos.chat.CreateMessageRequest;
import com.egorhristoforov.eventikrestapi.dtos.chat.UpdateMessageRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.news.NewsCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.news.NewsUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.chat.MessageListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.chat.MessageResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.news.NewsCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.news.NewsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.news.NewsUpdateResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.models.News;
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

    @GetMapping(value = "/{id}/news", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get news list", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<List<NewsListResponse>> getNewsList(@PathVariable(value = "id") @Positive Long eventId,
                                                              @RequestParam(value = "count", required = false) @Positive Long count,
                                                              @RequestParam(value = "last-news-id", required = false) @Positive Long lastNewsId) {
        return null;
    }

    @DeleteMapping(value = "/{id}/news/{newsId}")
    @ApiOperation(value = "Delete news by id", authorizations = {@Authorization(value = "Access token")})
    public void deleteNewsById(@PathVariable(value = "id") @Positive Long eventId, @PathVariable(value = "newsId") @Positive Long newsId) {

    }

    @PutMapping(value = "/{id}/news/{newsId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update news by id", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<NewsUpdateResponse> updateNewsById(@PathVariable(value = "id") @Positive Long eventId,
                                                             @PathVariable(value = "newsId") @Positive Long newsId,
                                                             @Valid @RequestBody NewsUpdateRequest body) {
        return null;
    }

    @PostMapping(value = "/{id}/news", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create news", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<NewsCreateResponse> createNews(@PathVariable(value = "id") @Positive Long eventId,
                                                          @Valid @RequestBody NewsCreateRequest body) {
        return null;
    }

    @PostMapping(value = "/{id}/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Send message", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<MessageResponse> sendMessage(@PathVariable(value = "id") @Positive Long eventId,
                                                       @Valid @RequestBody CreateMessageRequest body) {
        return null;
    }

    @PutMapping(value = "/{id}/chat/{messId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Edit message by id", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<MessageResponse> editMessage(@PathVariable(value = "id") @Positive Long eventId,
                                                       @PathVariable(value = "messId") @Positive Long messageId,
                                                       @Valid @RequestBody UpdateMessageRequest body) {
        return null;
    }

    @DeleteMapping(value = "/{id}/chat/{messId}")
    @ApiOperation(value = "Delete message by id", authorizations = {@Authorization(value = "Access token")})
    public void deleteMessage(@PathVariable(value = "id") @Positive Long eventId,
                              @PathVariable(value = "messId") @Positive Long messageId) {

    }

    @GetMapping(value = "/{id}/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get messages list", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<List<MessageListResponse>> getMessagesList(@PathVariable(value = "id") @Positive Long eventId,
                                                               @RequestParam(value = "count", required = false) @Positive Long count,
                                                               @RequestParam(value = "lastMessageId", required = false) @Positive Long lastMessageId) {
        return null;
    }

    @GetMapping(value = "/{id}/visitors/blocked", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get blocked users list by event id", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<List<EventBlockedUsersListResponse>> getBlockedUsersForEvent(@PathVariable(value = "id") @Positive Long eventId) {
        return null;
    }

    @DeleteMapping(value = "/{id}/visitors/{userId}")
    @ApiOperation(value = "Delete user from event by user id", authorizations = {@Authorization(value = "Access token")})
    public void deleteEventUser(@PathVariable("id") @Positive Long eventId,
                                @PathVariable("userId") @Positive Long userId){

    }

    @PostMapping(value = "/{id}/visitors/{userId}/block", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add user by id to black list", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<EventBlockedUserResponse> addUserToBlackList(@PathVariable("id") @Positive Long eventId,
                                                                                  @PathVariable("userId") @Positive Long userId) {
        return null;
    }

    @DeleteMapping(value = "/{id}/visitors/{userId}/unblock")
    @ApiOperation(value = "Delete user by id from black list", authorizations = {@Authorization(value = "Access token")})
    public void deleteUserFromBlackList(@PathVariable("id") @Positive Long eventId,
                                                                                       @PathVariable("userId") @Positive Long userId) {

    }

}
