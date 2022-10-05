package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    //GET http://localhost:8080/users/121332-23214-12-1
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public User findById(@PathVariable String id) {
        return service.findById(id);
    }

    //GET http://localhost:8080/users/1231-2313/reviews
    @GetMapping("/{id}/reviews")
    @PreAuthorize("permitAll()")
    public List<Review> findAllUserReviews(@PathVariable String id) {
        return service.findAllUserReviews(id);
    }

    //GET http://localhost:8080/users/1231-2313/reviews/24324-2102-41
    @GetMapping("/{userId}/reviews/{reviewId}")
    @PreAuthorize("permitAll()")
    public Review findUserReview(@PathVariable String userId, @PathVariable String reviewId) {
        return service.findUserReview(userId, reviewId);
    }

    //POST http://localhost:8080/users/22-131-23/reviews
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reviews")
    @PreAuthorize("hasRole('ROLE_PEASANT')")
    public Review createReview(@PathVariable String id, @RequestBody Map<String, String> json) {
        return service.createReview(id, json);
    }

    //PUT http://localhost:8080/users/213-123-1-24
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PEASANT')")
    public void update(@RequestBody Map<String, String> json, @PathVariable String id, Principal principal) throws Exception {
        try {
            if (id.equals(service.findByUsername(principal.getName()).getId())) {
                service.update(json, id);
            } else {
                throw new Exception("Ty kto takoy suka chtob eto delat!?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //PUT http://localhost:8080/123-231-421/reviews/23412-13-1324
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userId}/reviews/{reviewId}")
    @PreAuthorize("hasRole('ROLE_PEASANT')")
    public void updateReview(@RequestBody Map<String, String> json, @PathVariable String userId, @PathVariable String reviewId, Principal principal) throws Exception {
        try {
            if (userId.equals(service.findByUsername(principal.getName()).getId())) {
                service.updateReview(json, userId, reviewId);
            } else {
                throw new Exception("Ty kto takoy suka chtob eto delat!?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DELETE http://localhost:8080/users/14-1234-134
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PEASANT')")
    public void delete(@PathVariable String id, Principal principal) throws Exception {
        try {
            if (id.equals(service.findByUsername(principal.getName()).getId())) {
                service.delete(id);
            } else {
                throw new Exception("Ty kto takoy suka chtob eto delat!?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DELETE http://localhost:8080/users/242-124-124-5-2/reviews/234-523-43
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/reviews/{reviewId}")
    @PreAuthorize("hasRole('ROLE_PEASANT')")
    public void deleteReview(@PathVariable String userId, @PathVariable String reviewId, Principal principal) throws Exception {
        try {
            if (userId.equals(service.findByUsername(principal.getName()).getId())) {
                service.deleteReview(userId, reviewId);
            } else {
                throw new Exception("Ty kto takoy suka chtob eto delat!?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
