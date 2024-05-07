package com.biog.backend.auth;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String lastName;

  private String firstName;

  private String password;

  private String email;

  private UUID school;

  private UUID club;

  private int numApogee;

  private String cne;

  private String profileImage;

  private String clubName;

  private String clubLogo;

  private String clubDescription;

  private String clubBanner;
}
