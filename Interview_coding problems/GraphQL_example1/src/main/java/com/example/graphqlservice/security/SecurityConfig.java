package com.example.graphqlservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // GraphQL is typically called via POST; disable CSRF for the GraphQL endpoint.
        .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/graphql")))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/graphiql", "/graphiql/**").permitAll()
            .requestMatchers("/actuator/health").permitAll()
            .requestMatchers("/graphql").authenticated()
            .anyRequest().denyAll()
        )
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
