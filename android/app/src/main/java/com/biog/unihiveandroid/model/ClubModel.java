package com.biog.unihiveandroid.model;

public class ClubModel {
    private String clubName;
    private float clubRating;
    private int imgId;

    public ClubModel(float clubRating, int imgId) {
        this.clubRating = clubRating;
        this.imgId = imgId;
    }

    public ClubModel(String clubName, float clubRating, int imgId) {
        this.clubName = clubName;
        this.clubRating = clubRating;
        this.imgId = imgId;
    }

    public String getClubName() {
        return clubName;
    }
    public float getClubRating() {
        return clubRating;
    }

    public int getImgId() {
        return imgId;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public void setClubRating(float clubRating) {
        this.clubRating = clubRating;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
