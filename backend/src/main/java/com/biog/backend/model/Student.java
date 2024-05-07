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
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "students", schema = "public")
public class Student implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "cne", nullable = false, unique = true)
  private String cne;

  @Column(name = "num_apogee", nullable = false, unique = true)
  private int numApogee;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Builder.Default
  @Column(name = "profile_image")
  private String profileImage = "https://storage.googleapis.com/unihive-files/pfp-plaveholder.jpg";

  @ManyToMany
  @JoinTable(name = "follows", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "club_id"))
  @JsonIgnore
  private List<Club> clubs;

  @ManyToOne
  @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false)
  private School school;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private Role role = Role.STUDENT;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
  }

  public List<Club> getClubsBySchool(UUID schoolId) {
    return clubs.stream()
            .filter(club -> club.getSchool().getId().equals(schoolId))
            .collect(Collectors.toList());
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
