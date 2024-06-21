package com.biog.unihiveandroid.model;

import java.time.Instant;

public class UpcomingEventModel {
    private float eventRating;
    private String eventTitle;
    private Instant eventDate;
    private String clubName;
    private int imgId;

    public UpcomingEventModel(float eventRating, String eventTitle, int imgId) {
        this.eventRating = eventRating;
        this.eventTitle = eventTitle;
        this.imgId = imgId;
    }
    public UpcomingEventModel(String eventTitle, Instant eventDate, float eventRating, String clubName, int imgId) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventRating = eventRating;
        this.clubName = clubName;
        this.imgId = imgId;
    }

    public float getEventRating() {
        return eventRating;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public Instant getEventDate() {
        return eventDate;
    }
    public String getClubName() {
        return clubName;
    }
    public int getImgId() {
        return imgId;
    }
    public void setEventRating(float eventRating) {
        this.eventRating = eventRating;
    }
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
