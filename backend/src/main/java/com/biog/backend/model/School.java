package com.biog.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "schools", schema = "public")
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "school_name", nullable = false, unique = true)
  private String schoolName;

  @Column(name = "school_city", nullable = false)
  private String schoolCity;

  @Column(name = "school_address", nullable = false)
  private String schoolAddress;

  @OneToOne(mappedBy = "school", cascade = CascadeType.ALL)
  @JsonIgnore
  private Admin admin;

  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Club> clubs;

  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Student> students;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
  }
}
