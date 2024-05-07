package com.biog.backend.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register/admin")
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  public ResponseEntity<AuthenticationResponse> registerAdmin(
          @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.registerAdmin(request));
  }

  @PostMapping("/register/student")
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  public ResponseEntity<AuthenticationResponse> registerStudent(
          @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.registerStudent(request));
  }

  @PostMapping("/register/club")
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  public ResponseEntity<AuthenticationResponse> registerClub(
          @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.registerClub(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/changePassword")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT', 'ROLE_CLUB')")
  public ResponseEntity<AuthenticationResponse> changePassword(
          @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.changePassword(request));
  }

  @PostMapping("/forgotPassword")
  public ResponseEntity<AuthenticationResponse> forgotPassword(
          @RequestBody AuthenticationRequest request) throws MessagingException {
    return ResponseEntity.ok(service.forgotPassword(request));
  }

  @PostMapping("/register/superadmin")
  public ResponseEntity<AuthenticationResponse> registerSuperAdmin(
          @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.registerSuperAdmin(request));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(
          @RequestBody RegisterRequest request) {
    return service.signup(request);
  }

  @PutMapping("/acceptrequest/{id}")
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  public ResponseEntity<String> acceptRequest(
          @PathVariable UUID id) {
    return service.acceptRequest(id);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    return service.logout();
  }
}
