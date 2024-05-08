package com.biog.unihiveandroid.model;

import java.util.UUID;

public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UUID school;
    private UUID club;
    private int numApogee;
    private String cne;
    private String profileImage;
    private String clubName;
    private String clubLogo;
    private String clubDescription;
    private String clubBanner;
    private String schoolName;
    private String schoolCard;

    public RegisterRequest(String firstName, String lastName, String email, String password, UUID school, UUID club, int numApogee, String cne, String profileImage, String clubName, String clubLogo, String clubDescription, String clubBanner, String schoolName, String schoolCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.school = school;
        this.club = club;
        this.numApogee = numApogee;
        this.cne = cne;
        this.profileImage = profileImage;
        this.clubName = clubName;
        this.clubLogo = clubLogo;
        this.clubDescription = clubDescription;
        this.clubBanner = clubBanner;
        this.schoolName = schoolName;
        this.schoolCard = schoolCard;
    }

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterRequest(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UUID getSchool() {
        return school;
    }

    public UUID getClub() {
        return club;
    }

    public int getNumApogee() {
        return numApogee;
    }

    public String getCne() {
        return cne;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public String getClubBanner() {
        return clubBanner;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolCard() {
        return schoolCard;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchool(UUID school) {
        this.school = school;
    }

    public void setClub(UUID club) {
        this.club = club;
    }

    public void setNumApogee(int numApogee) {
        this.numApogee = numApogee;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public void setClubBanner(String clubBanner) {
        this.clubBanner = clubBanner;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setSchoolCard(String schoolCard) {
        this.schoolCard = schoolCard;
    }
}
