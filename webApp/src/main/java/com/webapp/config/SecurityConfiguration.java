package com.webapp.config;

import com.webapp.services.AuthentificationService;
import com.webapp.services.MsstatsClient;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Map;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthentificationService authentificationService;
    @Autowired
    private MsstatsClient msstatsClient;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));

            //  Envoyer l'événement connexion à msstats (async pour ne pas bloquer)
            try {
                String email = authentication.getName();
                String ip = request.getRemoteAddr();
                msstatsClient.recordConnexion(Map.of(
                        "userEmail", email,
                        "ipAddress", ip != null ? ip : "unknown"
                ));
            } catch (Exception e) {
                // Ne pas bloquer le login si msstats est indisponible
            }

            if (isAdmin) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/css/**",
                                "/images/**",
                                "/js/**",
                                "/favicon.ico",
                                "/actuator/health",
                                "/signin",
                                "/signup"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/signin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler())
                        .failureUrl("/signin?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/signin")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/signin")
                );

        return http.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            @Nonnull AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}