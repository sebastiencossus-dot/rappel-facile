package com.webapp.services;

import com.webapp.models.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor  // ✅ remplace @Autowired + constructeur manquant
public class AuthentificationService implements UserDetailsService {

    private final MsJpaClient msJpaClient;


    private static final Logger log = LoggerFactory.getLogger(AuthentificationService.class);

    @Override
    @Cacheable(value = "userDetails", key = "#email", unless = "#result == null")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = msJpaClient.findUserByEmail(email);
            log.info("ROLE RECU DE MSJPA = {}", user.getRole());

            if (user == null || user.getEmail() == null || user.getPassword() == null) {
                log.warn("Utilisateur introuvable ou incomplet : {}", email);
                throw new UsernameNotFoundException("Utilisateur invalide");
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );

        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erreur auth {} : {}", email, e.getMessage(), e);
            throw new UsernameNotFoundException("Erreur auth user: " + email, e);
        }
    }
}