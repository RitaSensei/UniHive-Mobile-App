package com.biog.backend.config;

import lombok.RequiredArgsConstructor;
import org.biog.unihivebackend.model.Admin;
import org.biog.unihivebackend.model.Professor;
import org.biog.unihivebackend.model.Student;
import org.biog.unihivebackend.repository.AdminRepository;
import org.biog.unihivebackend.repository.ProfessorRepository;
import org.biog.unihivebackend.repository.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  public final AdminRepository adminRepository;
  public final StudentRepository studentRepository;

  public final ProfessorRepository professorRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      Optional<Admin> admin = adminRepository.findByEmail(username);
      if (admin.isPresent()) {
        return admin.get();
      } else {
        Optional<Professor> professor = professorRepository.findByEmail(
          username
        );
        if (professor.isPresent()) {
          return professor.get();
        } else {
          Optional<Student> student = studentRepository.findByEmail(username);
          if (student.isPresent()) {
            return student.get();
          } else {
            return null;
          }
        }
      }
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration config
  ) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
