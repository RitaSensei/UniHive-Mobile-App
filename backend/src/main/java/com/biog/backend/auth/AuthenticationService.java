package com.biog.backend.auth;

import ch.qos.logback.core.spi.ErrorCodes;
import com.biog.backend.exception.NotFoundException;
import com.biog.backend.repository.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import com.biog.backend.config.JwtAuthenticationFilter;
import com.biog.backend.config.JwtService;
import com.biog.backend.email.EmailService;
import com.biog.backend.model.*;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final AdminRepository adminRepository;
  private final StudentRepository studentRepository;
  private final ClubRepository clubRepository;
  private final SchoolRepository schoolRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;

  public AuthenticationResponse registerAdmin(RegisterRequest request) {
    var user = User
            .builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ADMIN)
            .build();

    userRepository.save(user);

    var admin = Admin
            .builder()
            .lastName(request.getLastName())
            .firstName(request.getFirstName())
            .user(user)
            .school(schoolRepository.findById(request.getSchool()).orElseThrow(
                    () -> new NotFoundException(
                            "School not found with id " + request.getSchool())))
            .build();

    adminRepository.save(admin);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse registerStudent(RegisterRequest request) {
    var user = User
            .builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.STUDENT)
            .build();

    userRepository.save(user);

    var student = Student
            .builder()
            .lastName(request.getLastName())
            .firstName(request.getFirstName())
            .cne(request.getCne())
            .numApogee(request.getNumApogee())
            .profileImage(request.getProfileImage())
            .school(schoolRepository.findById(request.getSchool()).orElseThrow(
                    () -> new NotFoundException(
                            "School not found with id " + request.getSchool())))
            .user(user)
            .build();
    studentRepository.save(student);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse registerClub(RegisterRequest request) {
    var user = User
            .builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.CLUB)
            .build();

    userRepository.save(user);

    var club = Club
            .builder()
            .clubName(request.getClubName())
            .clubLogo(request.getClubLogo())
            .clubDescription(request.getClubDescription())
            .clubBanner(request.getClubBanner())
            .user(user)
            .school(schoolRepository.findById(request.getSchool()).orElseThrow(
                    () -> new NotFoundException(
                            "School not found with id " + request.getSchool())))
            .build();
    clubRepository.save(club);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()));

    var user = userRepository.findByEmail(request.getEmail());
    if (user.isPresent()
            && Arrays.asList("SUPER_ADMIN", "ADMIN", "STUDENT", "CLUB").contains(user.get().getRole().toString())) {
      var jwtToken = jwtService.generateToken(user.get());
      return AuthenticationResponse.builder().token(jwtToken).build();
    } else {
      return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
    }
  }

  public AuthenticationResponse changePassword(AuthenticationRequest request) {
    var user = userRepository.findByEmail(jwtAuthFilter.getCurrentUserEmail());
    if (user.isPresent() &&
            Arrays.asList("SUPER_ADMIN", "ADMIN", "STUDENT", "CLUB").contains(user.get().getRole().toString())) {
      if (passwordEncoder.matches(
              request.getOldPassword(),
              user.get().getPassword())) {
        user
                .get()
                .setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user.get());
        var jwtToken = jwtService.generateToken(user.get());
        return AuthenticationResponse.builder().token(jwtToken).build();
      } else {
        return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
      }
    } else {
      return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
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
            digitRule);
    return password;
  }

  public AuthenticationResponse forgotPassword(AuthenticationRequest request)
          throws MessagingException {
    var user = userRepository.findByEmail(request.getEmail());
    if (user.isPresent()) {
      String newPassword = generatePassayPassword();
      user.get().setPassword(passwordEncoder.encode(newPassword));
      emailService.sendEmail(
              request.getEmail(),
              "UniHive Corporation",
              "Your new password is " +
                      newPassword +
                      "." +
                      " Please change it after logging in.");
      userRepository.save(user.get());
      var jwtToken = jwtService.generateToken(user.get());
      return AuthenticationResponse.builder().token(jwtToken).build();
    } else {
      return AuthenticationResponse.builder().token("INVALID_TOKEN").build();
    }
  }
}
