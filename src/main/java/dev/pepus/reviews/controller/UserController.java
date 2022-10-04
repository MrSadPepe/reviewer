package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_PEASANT')")
public class UserController {

    @Autowired
    private UserService service;

    //GET http://localhost:8080/users/121332-23214-12-1
    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return service.findById(id);
    }

    //GET http://localhost:8080/users/1231-2313/reviews
    @GetMapping("/{id}/reviews")
    public List<Review> findAllUserReviews(@PathVariable String id) {
        return service.findAllUserReviews(id);
    }

    //GET http://localhost:8080/users/1231-2313/reviews/24324-2102-41
    @GetMapping("/{userId}/reviews/{reviewId}")
    public Review findUserReview(@PathVariable String userId, @PathVariable String reviewId) {
        return service.findUserReview(userId, reviewId);
    }

    //POST http://localhost:8080/users/22-131-23/reviews
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reviews")
    public Review createReview(@PathVariable String id, @RequestBody Map<String, String> json) {
        return service.createReview(id, json);
    }

    //PUT http://localhost:8080/users/213-123-1-24
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Map<String, String> json,@PathVariable String id) {
        service.update(json, id);
    }

    //PUT http://localhost:8080/123-231-421/reviews/23412-13-1324
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userId}/reviews/{reviewId}")
    public void updateReview(@RequestBody Map<String, String> json, @PathVariable String userId, @PathVariable String reviewId){
        service.updateReview(json, userId, reviewId);
    }

    //DELETE http://localhost:8080/users/14-1234-134
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    //DELETE http://localhost:8080/users/242-124-124-5-2/reviews/234-523-43
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/reviews/{reviewId}")
    public void deleteReview(@PathVariable String userId, @PathVariable String reviewId) {
        service.deleteReview(userId, reviewId);
    }
}
