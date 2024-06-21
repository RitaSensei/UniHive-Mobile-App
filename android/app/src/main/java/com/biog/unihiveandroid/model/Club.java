package com.biog.unihiveandroid.model;

import java.time.Instant;
import java.util.List;

public class Club {
    private String id;
    private String clubName;
    private String email;
    private String password;
    private Role role;
    private String clubLogo;
    private String clubDescription;
    private String clubBanner;
    private float clubRating;
    private int ratingCount;
    private Instant createdAt;
    private School school;
    private List<Student> students;
    private List<Event> events;

    public Club() {
    }

    public Club(String id, String clubName, String email, String password, Role role, String clubLogo, String clubDescription, String clubBanner, float clubRating, int ratingCount, Instant createdAt, School school, List<Student> students, List<Event> events) {
        this.id = id;
        this.clubName = clubName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.clubLogo = clubLogo;
        this.clubDescription = clubDescription;
        this.clubBanner = clubBanner;
        this.clubRating = clubRating;
        this.ratingCount = ratingCount;
        this.createdAt = createdAt;
        this.school = school;
        this.students = students;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubBanner() {
        return clubBanner;
    }

    public void setClubBanner(String clubBanner) {
        this.clubBanner = clubBanner;
    }

    public float getClubRating() {
        return clubRating;
    }

    public void setClubRating(float clubRating) {
        this.clubRating = clubRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
