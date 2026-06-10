package com.webapp.services;


import com.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.webapp.services.form.SignUpForm;

@Service
public class UserService {

    @Autowired
    MsJpaClient msJpaClient;

    @Autowired
    PasswordEncoder passwordEncoder;



    public User registration(SignUpForm form) {



        User user = new User();

        user.setPrenom(form.getPrenom());
        user.setNom(form.getNom());
        user.setEmail(form.getEmail());
        user.setTel(form.getTel());



        user.setPassword(passwordEncoder.encode(form.getPassword()));
        return msJpaClient.createUser(user);


    }

    public void changePassword(User user,
                               String oldPassword,
                               String newPassword,
                               String confirmPassword) {

        // Vérifier ancien mot de passe
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        // Vérifier confirmation
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Les mots de passe ne correspondent pas");
        }

        // Sécurité minimum
        if (newPassword.length() < 6) {
            throw new RuntimeException("Mot de passe trop court (min 6 caractères)");
        }

        // Hash + sauvegarde
        user.setPassword(passwordEncoder.encode(newPassword));
        msJpaClient.createUser(user);
    }
}
