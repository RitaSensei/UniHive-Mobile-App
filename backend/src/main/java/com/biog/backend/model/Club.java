package com.biog.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "clubs", schema = "public")
public class Club {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "club_name", nullable = false, unique = true)
  private String clubName;

  @Column(name = "club_logo", nullable = false)
  private String clubLogo;

  @Column(name = "club_description", nullable = false)
  private String clubDescription;

  @Column(name = "club_banner", nullable = false)
  private String clubBanner;

  @Column(name = "club_rating")
  private float clubRating;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(
    name = "student_id",
    referencedColumnName = "id",
    nullable = false
  )
  private Student student_id;

  @OneToMany(mappedBy = "club_id")
  private List<Event> events;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false)
  private School school_id;
}
