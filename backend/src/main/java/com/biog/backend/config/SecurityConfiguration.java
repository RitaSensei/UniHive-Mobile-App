package com.biog.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(requests ->
        requests
          .requestMatchers("/auth/authenticate", "/auth/forgotPassword")
          .permitAll()
          .requestMatchers("/auth/register/**")
          .hasRole("ADMIN")
          .requestMatchers("/professor/**")
          .hasAnyRole("PROFESSOR", "ADMIN")
          .requestMatchers("/student/**")
          .hasAnyRole("STUDENT", "ADMIN")
          .requestMatchers("/auth/changePassword")
          .hasAnyRole("STUDENT", "PROFESSOR")
          .requestMatchers("/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .sessionManagement(management ->
        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(
        jwtAuthFilter,
        UsernamePasswordAuthenticationFilter.class
      );

    return http.build();
  }
}
