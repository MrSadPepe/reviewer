package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.RoleRepository;
import dev.pepus.reviews.repository.UserRepository;
import dev.pepus.reviews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/registration")
@PreAuthorize("permitAll()")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User create(@RequestBody Map<String, String> json){
        if (userRepository.existsByUsername(json.get("Username"))) {
            return null;
        }
        User user = userService.create(json);
        user.setRole(roleRepository.findByName("peasant").get());
        return userRepository.save(user);
    }
}
