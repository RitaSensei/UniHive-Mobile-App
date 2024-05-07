package com.biog.backend.config;

import com.biog.backend.model.Admin;
import com.biog.backend.model.Club;
import com.biog.backend.model.Student;
import com.biog.backend.model.SuperAdmin;
import com.biog.backend.repository.AdminRepository;
import com.biog.backend.repository.ClubRepository;
import com.biog.backend.repository.StudentRepository;
import com.biog.backend.repository.SuperAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  public final AdminRepository adminRepository;
  public final ClubRepository clubRepository;
  public final StudentRepository studentRepository;
  public final SuperAdminRepository superAdminRepository;

  @Bean
  public UserDetailsService userDetailsService() throws UsernameNotFoundException {
    return username -> {
      Optional<Admin> admin = adminRepository.findByEmail(username);
      if (admin.isPresent()) {
        return admin.get();
      } else {
        Optional<Club> club = clubRepository.findByEmail(username);
        if (club.isPresent()) {
          return club.get();
        } else {
          Optional<Student> student = studentRepository.findByEmail(username);
          if (student.isPresent()) {
            return student.get();
          } else {
            Optional<SuperAdmin> superAdmin = superAdminRepository.findByEmail(username);
            if (superAdmin.isPresent()) {
              return superAdmin.get();
            } else {
              throw new UsernameNotFoundException("User not found");
            }
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
          AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

