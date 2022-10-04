package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@PreAuthorize("permitAll()")
public class ReviewController {

    @Autowired
    private ReviewService service;

    //GET http://localhost:8080/reviews
    @GetMapping
    public List<Review> findAll() {
        return service.findAll();
    }

    //GET http://localhost:8080/reviews/1242-2342-234234-234
    @GetMapping("/{id}")
    public Review findById(@PathVariable String id) {
        return service.findById(id);
    }

}
