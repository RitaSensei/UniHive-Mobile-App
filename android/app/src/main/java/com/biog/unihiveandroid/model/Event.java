package com.biog.unihiveandroid.model;

import java.time.Instant;

public class Event {
    private String id;
    private Instant createdAt;
    private String eventName;
    private String eventCategory;
    private String eventDescription;
    private String eventLocation;
    private String eventBanner;
    private Instant startTime;
    private Instant endTime;
    private float eventRating;
    private int ratingCount;
    private Club club;

    public Event() {
    }

    public Event(String id, Instant createdAt, String eventName, String eventCategory, String eventDescription, String eventLocation, String eventBanner, Instant startTime, Instant endTime, float eventRating, int ratingCount, Club club) {
        this.id = id;
        this.createdAt = createdAt;
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.eventBanner = eventBanner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventRating = eventRating;
        this.ratingCount = ratingCount;
        this.club = club;
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventBanner() {
        return eventBanner;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public float getEventRating() {
        return eventRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public Club getClub() {
        return club;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public void setEventBanner(String eventBanner) {
        this.eventBanner = eventBanner;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public void setEventRating(float eventRating) {
        this.eventRating = eventRating;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
