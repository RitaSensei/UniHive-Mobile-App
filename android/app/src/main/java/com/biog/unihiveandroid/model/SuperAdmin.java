package com.biog.unihiveandroid.model;

import java.time.Instant;

public class SuperAdmin {
    private String id;
    private Instant createdAt;
    public SuperAdmin(String id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
