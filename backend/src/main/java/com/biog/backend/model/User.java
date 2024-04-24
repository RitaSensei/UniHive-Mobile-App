package com.biog.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "auth")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "encrypted_password", nullable = false, unique = true)
  private String password;

  @OneToOne(mappedBy = "user")
  @JsonBackReference(value = "user-admin")
  private Admin admin;

  @OneToOne(mappedBy = "user")
  @JsonBackReference(value = "user-superadmin")
  private SuperAdmin superAdmin;

  @OneToOne(mappedBy = "user")
  @JsonBackReference(value = "user-student")
  private Student student;

  @OneToOne(mappedBy = "user")
  @JsonBackReference(value = "user-club")
  private Club club;

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