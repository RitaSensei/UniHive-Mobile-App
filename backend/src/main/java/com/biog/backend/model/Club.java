package com.biog.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "clubs", schema = "public")
public class Club implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "club_name", nullable = false, unique = true)
  private String clubName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "club_logo", nullable = false)
  private String clubLogo;

  @Column(name = "club_description", nullable = false)
  private String clubDescription;

  @Column(name = "club_banner", nullable = false)
  private String clubBanner;

  @Column(name = "club_rating")
  private float clubRating;

  @Column(name = "rating_count")
  private int ratingCount;

  @ManyToMany
  @JoinTable(name = "follows", joinColumns = @JoinColumn(name = "club_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
  private List<Student> students;

  @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Event> events;

  @ManyToOne
  @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false)
  private School school;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private Role role = Role.CLUB;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
