package com.biog.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "events", schema = "public")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "event_category", nullable = false)
  private String eventCategory;

  @Column(name = "event_name", nullable = false)
  private String eventName;

  @Column(name = "event_description", nullable = false)
  private String eventDescription;

  @Column(name = "event_banner", nullable = false)
  private String eventBanner;

  @Column(name = "start_time")
  private Instant startTime;

  @Column(name = "end_time")
  private Instant endTime;

  @Column(name = "event_rating")
  private float eventRating;

  @Column(name = "rating_count")
  private int ratingCount;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "club_id", referencedColumnName = "id", nullable = false)
  @JsonManagedReference(value = "club-event")
  private Club club;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
  }
}
