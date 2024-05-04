package org.skywise.skywise.controllers;

import org.skywise.skywise.models.User;
import org.skywise.skywise.repositories.UserRepository;
import org.skywise.skywise.security.SharedConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, SharedConfig sharedConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = sharedConfig.passwordEncoder();
    }

    @GetMapping("/create")
    public String createUser() {
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(
            @RequestParam() String username,
            @RequestParam() String password,
            @RequestParam() String firstName,
            @RequestParam() String lastName,
            @RequestParam() String email,
            @RequestParam() String role
    ) {
        User user = new User();
        user.setUsername(username);
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);
        userRepository.save(user);

        return "redirect:/user/";
    }

    @GetMapping("/")
    public String usersList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/update/{userID}")
    public String updateUser(
            Model model,
            @PathVariable Long userID
    ) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isEmpty()) {
            return "redirect:/user/";
        }

        model.addAttribute("user", userOptional.get());
        return "user/update";
    }

    @PostMapping("/update/{userID}")
    public String updateUser(
            @PathVariable Long userID,
            @RequestParam() String username,
            @RequestParam(required = false) String password,
            @RequestParam() String firstName,
            @RequestParam() String lastName,
            @RequestParam() String email,
            @RequestParam() String role
    ) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setUsername(username);
            if (password != null) {
                password = passwordEncoder.encode(password);
                user.setPassword(password);
            }
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setRole(role);

            userRepository.save(user);
        }

        return "redirect:/user/";
    }

    @GetMapping("/delete/{userID}")
    public String deleteUser(@PathVariable Long userID) {
        userRepository.deleteById(userID);
        return "redirect:/user/";
    }
}
