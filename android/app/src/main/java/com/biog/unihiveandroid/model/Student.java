package com.biog.unihiveandroid.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Student {
    private String id;
    private String email;
    private String password;
    private Instant createdAt;
    private String cne;
    private int numApogee;
    private String firstName;
    private String lastName;
    private String profileImage;
    private List<Club> clubs;
    private School school;
    private Role role=Role.STUDENT;

    public Student(String id, String email, String password, Instant createdAt, String cne, int numApogee, Role role, String firstName, String lastName, String profileImage, List<Club> clubs, School school) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.cne = cne;
        this.numApogee = numApogee;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.clubs = clubs;
        this.school = school;
    }

    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
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
    public Role getRole() {
        return role;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public List<Club> getClubs() {
        return clubs;
    }
    public School getSchool() {
        return school;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public void setRole(Role role) {
        this.role = role;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
    public void setSchool(School school) {
        this.school = school;
    }
}
