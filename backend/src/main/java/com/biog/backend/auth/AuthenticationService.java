package com.biog.backend.auth;

import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import com.biog.backend.config.JwtAuthenticationFilter;
import com.biog.backend.config.JwtService;
import com.biog.backend.email.EmailService;
import com.biog.backend.model.Admin;
import com.biog.backend.model.Student;
import com.biog.backend.repository.AdminRepository;
import com.biog.backend.repository.StudentRepository;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AdminRepository adminRepository;
  private final StudentRepository studentRepository;
  private final ProfessorRepository professorRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;

  public AuthenticationResponse registerAdmin(RegisterRequest request) {
    var admin = Admin
      .builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .build();
    adminRepository.save(admin);
    var jwtToken = jwtService.generateToken(admin);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse registerStudent(RegisterRequest request) {
    var student = Student
      .builder()
      .lastName(request.getLastName())
      .firstName(request.getFirstName())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .address(request.getAddress())
      .dateOfBirth(request.getDateOfBirth())
      .phoneNumber(request.getPhoneNumber())
      .gender(request.getGender())
      .build();
    studentRepository.save(student);
    var jwtToken = jwtService.generateToken(student);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse registerProfessor(RegisterRequest request) {
    var professor = Professor
      .builder()
      .lastName(request.getLastName())
      .firstName(request.getFirstName())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .address(request.getAddress())
      .phoneNumber(request.getPhoneNumber())
      .build();
    professorRepository.save(professor);
    var jwtToken = jwtService.generateToken(professor);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );

    var admin = adminRepository.findByEmail(request.getEmail());
    if (admin.isPresent()) {
      var jwtToken = jwtService.generateToken(admin.get());
      return AuthenticationResponse.builder().token(jwtToken).build();
    } else {
      var professor = professorRepository.findByEmail(request.getEmail());
      if (professor.isPresent()) {
        var jwtToken = jwtService.generateToken(professor.get());
        return AuthenticationResponse.builder().token(jwtToken).build();
      } else {
        var student = studentRepository.findByEmail(request.getEmail());
        if (student.isPresent()) {
          var jwtToken = jwtService.generateToken(student.get());
          return AuthenticationResponse.builder().token(jwtToken).build();
        } else {
          return AuthenticationResponse
            .builder()
            .token("INVALID_TOKEN")
            .build();
        }
      }
    }
  }

  public AuthenticationResponse changePassword(AuthenticationRequest request) {
    var professor = professorRepository.findByEmail(
      jwtAuthFilter.getCurrentUserEmail()
    );
    if (professor.isPresent()) {
      if (
        passwordEncoder.matches(
          request.getOldPassword(),
          professor.get().getPassword()
        )
      ) {
        professor
          .get()
          .setPassword(passwordEncoder.encode(request.getNewPassword()));
        professorRepository.save(professor.get());
        var jwtToken = jwtService.generateToken(professor.get());
        return AuthenticationResponse.builder().token(jwtToken).build();
      }
      return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
    } else {
      var student = studentRepository.findByEmail(
        jwtAuthFilter.getCurrentUserEmail()
      );
      if (student.isPresent()) {
        if (
          passwordEncoder.matches(
            request.getOldPassword(),
            student.get().getPassword()
          )
        ) {
          student
            .get()
            .setPassword(passwordEncoder.encode(request.getNewPassword()));
          studentRepository.save(student.get());
          var jwtToken = jwtService.generateToken(student.get());
          return AuthenticationResponse.builder().token(jwtToken).build();
        }
        return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
      } else {
        return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
      }
    }
  }

  public String generatePassayPassword() {
    PasswordGenerator gen = new PasswordGenerator();
    CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
    lowerCaseRule.setNumberOfCharacters(2);

    CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
    upperCaseRule.setNumberOfCharacters(2);

    CharacterData digitChars = EnglishCharacterData.Digit;
    CharacterRule digitRule = new CharacterRule(digitChars);
    digitRule.setNumberOfCharacters(2);

    CharacterData specialChars = new CharacterData() {
      public String getErrorCode() {
        return ErrorCodes.EMPTY_MODEL_STACK;
      }

      public String getCharacters() {
        return "!@#$%^&*()_+";
      }
    };
    CharacterRule splCharRule = new CharacterRule(specialChars);
    splCharRule.setNumberOfCharacters(2);

    String password = gen.generatePassword(
      10,
      splCharRule,
      lowerCaseRule,
      upperCaseRule,
      digitRule
    );
    return password;
  }

  public AuthenticationResponse forgotPassword(AuthenticationRequest request)
    throws MessagingException {
    var professor = professorRepository.findByEmail(request.getEmail());
    if (professor.isPresent()) {
      String newPassword = generatePassayPassword();
      professor.get().setPassword(passwordEncoder.encode(newPassword));
      emailService.sendEmail(
        request.getEmail(),
        "Internship Management System",
        "Your new password is " +
        newPassword +
        "." +
        " Please change it after logging in."
      );
      professorRepository.save(professor.get());
      var jwtToken = jwtService.generateToken(professor.get());
      return AuthenticationResponse.builder().token(jwtToken).build();
    } else {
      var student = studentRepository.findByEmail(request.getEmail());
      if (student.isPresent()) {
        String newPassword = generatePassayPassword();
        student.get().setPassword(passwordEncoder.encode(newPassword));
        emailService.sendEmail(
          request.getEmail(),
          "Internship Management System",
          "Your new password is " +
          newPassword +
          "." +
          " Please change it after logging in."
        );
        studentRepository.save(student.get());
        var jwtToken = jwtService.generateToken(student.get());
        return AuthenticationResponse.builder().token(jwtToken).build();
      } else {
        return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
      }
    }
  }
}
