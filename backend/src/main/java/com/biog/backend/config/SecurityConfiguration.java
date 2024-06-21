package com.biog.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/auth/authenticate", "/auth/signup", "/auth/forgotPassword", "/auth/register/**",
                            "/auth/acceptrequest/**", "/admin/**", "/auth/register/admin", "/auth/register/student",
                            "/auth/register/club", "/auth/changePassword", "/auth/logout", "/superadmin/**", "/upload/**",
                            "/download/**", "/delete/**", "/list/**", "/file/**", "/club/**", "/event/**", "/student/**",
                            "/student/events", "/student/club/**", "/student/**", "/student/uppassword")
                    .permitAll()
                    .anyRequest()
                    .authenticated())

            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(
                    jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
