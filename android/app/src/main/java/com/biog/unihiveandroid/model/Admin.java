package com.biog.unihiveandroid.model;

import java.time.Instant;

public class Admin {
    private String id;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private Instant createdAt;
    private School school;
    public Admin(String id, String email, String password, Role role, String firstName, String lastName, Instant createdAt, School school) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
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
    public Role getRole() {
        return role;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public Instant getCreatedAt() {
        return createdAt;
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
    public void setRole(Role role) {
        this.role = role;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setSchool(School school) {
        this.school = school;
    }
}
