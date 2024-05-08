package com.biog.unihiveandroid.model;

import java.time.Instant;

public class School {
    private String id;
    private Instant createdAt;
    private String schoolName;
    private String schoolCity;
    private String schoolAddress;
    private Admin admin;
    private Club[] clubs;
    private Student[] students;
    public School(String id, Instant createdAt, String schoolName, String schoolCity, String schoolAddress, Admin admin, Club[] clubs, Student[] students) {
        this.id = id;
        this.createdAt = createdAt;
        this.schoolName = schoolName;
        this.schoolCity = schoolCity;
        this.schoolAddress = schoolAddress;
        this.admin = admin;
        this.clubs = clubs;
        this.students = students;
    }

    public String getId() {
        return id;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public String getSchoolName() {
        return schoolName;
    }
    public String getSchoolCity() {
        return schoolCity;
    }
    public String getSchoolAddress() {
        return schoolAddress;
    }
    public Admin getAdmin() {
        return admin;
    }
    public Club[] getClubs() {
        return clubs;
    }
    public Student[] getStudents() {
        return students;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }
    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public void setClubs(Club[] clubs) {
        this.clubs = clubs;
    }
    public void setStudents(Student[] students) {
        this.students = students;
    }
}
