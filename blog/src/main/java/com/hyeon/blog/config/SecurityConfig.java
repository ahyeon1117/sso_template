package com.hyeon.blog.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import org.springframework.security.oauth2.client.registration.ClientRegistration;
// import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
// import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
// import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String[] AUTH_WHITELIST = {
    "/login/**",
    "/index.html",
    "/user/**",
    "/js/**",
    "/css/**",
    "/html/**",
    "/fonts/**",
    "/images/**",
    "/error",
    "/oauth2/**",
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(login -> login.disable())
      .httpBasic(basic -> basic.disable())
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(request ->
        request
          .requestMatchers(AUTH_WHITELIST)
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .oauth2Login(login ->
        login
          .authorizationEndpoint(auth -> auth.baseUri("/oauth2/authorization"))
          .loginPage("/login")
      )
      .cors(cors -> cors.configurationSource(corsConfigurationSource()));

    return http.build();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    // Very permissive CORS config...
    final var configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(List.of("*"));

    // Limited to API routes (neither actuator nor Swagger-UI)
    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
