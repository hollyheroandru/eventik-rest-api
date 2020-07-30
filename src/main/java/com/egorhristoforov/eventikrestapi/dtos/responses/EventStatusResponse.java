package com.egorhristoforov.eventikrestapi.dtos.responses;

public class EventStatusResponse {
    private boolean booked;
    private boolean created;

    public EventStatusResponse() {
    }

    public EventStatusResponse(boolean booked, boolean created) {
        this.booked = booked;
        this.created = created;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}
