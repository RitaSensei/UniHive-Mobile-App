package com.biog.backend.model;

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

  @OneToOne(mappedBy = "school_id")
  private Admin admin;

  @OneToMany(mappedBy = "school_id")
  private List<Club> clubs;

  @OneToMany(mappedBy = "school_id")
  private List<Student> students;
}
