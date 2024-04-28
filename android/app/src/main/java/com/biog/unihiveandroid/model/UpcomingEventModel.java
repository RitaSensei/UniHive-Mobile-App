package com.biog.unihiveandroid.model;

public class UpcomingEventModel {
    private int eventRating;
    private String eventTitle;
    private int imgId;

    public UpcomingEventModel(int eventRating, String eventTitle, int imgId) {
        this.eventRating = eventRating;
        this.eventTitle = eventTitle;
        this.imgId = imgId;
    }

    public int getEventRating() {
        return eventRating;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public int getImgId() {
        return imgId;
    }
    public void setEventRating(int eventRating) {
        this.eventRating = eventRating;
    }
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
