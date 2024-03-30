package com.biog.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users", schema = "auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "role", nullable = false)
  private String role;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "encrypted_password", nullable = false, unique = true)
  private String password;
}
