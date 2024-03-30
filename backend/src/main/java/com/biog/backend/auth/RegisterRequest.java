package com.biog.backend.auth;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.biog.backend.model.Gender;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String lastName;

  private String firstName;

  private String address;

  private LocalDate dateOfBirth;

  private String password;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String phoneNumber;

  private String email;
}
