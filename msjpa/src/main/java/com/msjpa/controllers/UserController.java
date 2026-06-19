package com.msjpa.controllers;



import com.msjpa.models.User;
import com.msjpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    @GetMapping
    public User findByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @GetMapping("/all")
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + id));
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + id));

        user.setNom(updatedUser.getNom());
        user.setPrenom(updatedUser.getPrenom());
        user.setEmail(updatedUser.getEmail());
        user.setTel(updatedUser.getTel());
        user.setRole(updatedUser.getRole());
        // password non modifié ici, géré séparément via changePassword

        return userRepository.save(user);
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

}