package com.msjpa.controllers;



import com.msjpa.models.User;
import com.msjpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {

        System.out.println("RECU AVANT SAVE = " + user.getPassword());

        User saved = userRepository.save(user);

        System.out.println("APRES SAVE = " + saved.getPassword());

        return saved;
    }

    @GetMapping
    public User findByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}