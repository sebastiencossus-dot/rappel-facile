package com.webapp.config;

import com.webapp.services.AuthentificationService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthentificationService authentificationService;

    @Bean
    public PasswordEncoder passwordEncoder() throws Exception { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/bootstrap.min.css", "/index.css", "/images/**", "/signin", "/signup", "/js/**")
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .permitAll().anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/signin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/signin?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/signin")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .sessionManagement( session -> session
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
