package com.biog.unihiveandroid.model;

public class ClubModel {
    private int clubRating;
    private int imgId;

    public ClubModel(int clubRating, int imgId) {
        this.clubRating = clubRating;
        this.imgId = imgId;
    }

    public int getClubRating() {
        return clubRating;
    }

    public int getImgId() {
        return imgId;
    }
    public void setClubRating(int clubRating) {
        this.clubRating = clubRating;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
