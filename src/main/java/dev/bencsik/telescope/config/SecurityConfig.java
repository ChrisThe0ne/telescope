package dev.bencsik.telescope.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for API usage
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()  // Allow all API requests without authentication
                        .requestMatchers("/actuator/**").permitAll()  // Allow Actuator endpoints
                        .anyRequest().authenticated()  // Require authentication for everything else
                )
                .httpBasic(AbstractHttpConfigurer::disable)  // Disable basic auth (optional)
                .formLogin(AbstractHttpConfigurer::disable)  // Disable login form (optional)
                .build();
    }
}
