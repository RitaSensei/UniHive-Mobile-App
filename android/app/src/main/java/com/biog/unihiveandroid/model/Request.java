package com.biog.unihiveandroid.model;

import java.time.Instant;

public class Request {
    private int id;
    private Instant createdAt;
    private String cne;
    private int numApogee;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String schoolCard;
    private String schoolName;

    public Request(int id, Instant createdAt, String cne, int numApogee, String firstName, String lastName, String email, String password, String schoolCard, String schoolName) {
        this.id = id;
        this.createdAt = createdAt;
        this.cne = cne;
        this.numApogee = numApogee;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.schoolCard = schoolCard;
        this.schoolName = schoolName;
    }


    public Request() {
    }

    public int getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getCne() {
        return cne;
    }

    public int getNumApogee() {
        return numApogee;
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

    public String getSchoolCard() {
        return schoolCard;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public void setNumApogee(int numApogee) {
        this.numApogee = numApogee;
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

    public void setSchoolCard(String schoolCard) {
        this.schoolCard = schoolCard;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
