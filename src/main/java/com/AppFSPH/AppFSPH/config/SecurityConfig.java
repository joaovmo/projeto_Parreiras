package com.AppFSPH.AppFSPH.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:5173"); // Origem permitida
        configuration.addAllowedHeader("*"); // Permitir todos os cabeçalhos
        configuration.addAllowedMethod("*"); // Permitir todos os métodos (GET, POST, etc.)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuração de CORS
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login", "/v3/api-docs/*", "/swagger-ui/*", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginProcessingUrl("/login")
                .successHandler(customSuccessHandler())
                .failureHandler((request, response, exception) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", 0); // Substitua por um ID real se disponível
            userData.put("email", "string"); // Substitua por email real se disponível
            userData.put("password", userDetails.getPassword());
            userData.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
            userData.put("enabled", userDetails.isEnabled());
            userData.put("username", userDetails.getUsername());
            userData.put("authorities", userDetails.getAuthorities().stream()
                .map(authority -> Map.of("authority", authority.getAuthority()))
                .collect(Collectors.toList()));
            userData.put("credentialsNonExpired", userDetails.isCredentialsNonExpired());
            userData.put("accountNonExpired", userDetails.isAccountNonExpired());
            userData.put("passwordChanged", true); // Ajuste conforme necessário
            userData.put("accountNonLocked", userDetails.isAccountNonLocked());

            ObjectMapper mapper = new ObjectMapper();
            try {
                response.getWriter().write(mapper.writeValueAsString(userData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}