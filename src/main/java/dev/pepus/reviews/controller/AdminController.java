package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.RoleRepository;
import dev.pepus.reviews.repository.UserRepository;
import dev.pepus.reviews.service.ContentService;
import dev.pepus.reviews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody Map<String, String> json, @PathVariable String id){
        User user = userRepository.findById(id).get();
        if (json.get("password") != null){
            user.setPassword(bCryptPasswordEncoder.encode(json.get("password")));
        }
        if (json.get("role") != null){
            user.setRole(roleRepository.findByName(json.get("role")).get());
        }
        userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id){
        userService.delete(userRepository.findById(id).get().getId());
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@RequestBody Map<String, String> json, @PathVariable String id){
        userService.deleteReview(userRepository.findUserByUsername(json.get("username")).get().getId(), id);
    }

    @DeleteMapping("/contents/{id}")
    public void deleteContent(@PathVariable String id){
        contentService.delete(id);
    }

}
