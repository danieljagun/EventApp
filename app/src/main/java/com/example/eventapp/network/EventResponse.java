package com.example.eventapp.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.eventapp.models.Event;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventResponse {

    private int count;
    private boolean overflow;
    private String next; // Add the "next" field

    @JsonProperty("results")
    private List<Event> events; // Rename field to match JSON structure

    // Getters and setters for count, overflow, next, and events

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public void setOverflow(boolean overflow) {
        this.overflow = overflow;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
