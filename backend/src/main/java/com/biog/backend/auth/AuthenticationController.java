package com.biog.backend.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/auth/register/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<AuthenticationResponse> registerAdmin(
    @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerAdmin(request));
  }

  @PostMapping("/auth/register/student")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<AuthenticationResponse> registerStudent(
    @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerStudent(request));
  }

  @PostMapping("/auth/register/professor")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<AuthenticationResponse> registerProfessor(
    @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerProfessor(request));
  }

  @PostMapping("/auth/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
    @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/auth/changePassword")
  @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
  public ResponseEntity<AuthenticationResponse> changePassword(
    @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.changePassword(request));
  }

  @PostMapping("/auth/forgotPassword")
  public ResponseEntity<AuthenticationResponse> forgotPassword(
    @RequestBody AuthenticationRequest request
  ) throws MessagingException {
    return ResponseEntity.ok(service.forgotPassword(request));
  }
}
