package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.Content;
import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contents")
@PreAuthorize("permitAll()")
public class ContentController {
    @Autowired
    private ContentService service;

    //GET http://localhost:8080/contents
    @GetMapping
    public List<Content> findAll(){
        return service.findAll();
    }

    //GET http://localhost:8080/contents/123-123-2
    @GetMapping("/{id}")
    public Content findById(@PathVariable String id){
        return service.findById(id);
    }

    //GET http://localhost:8080/contents/123-2141-24-123/reviews
    @GetMapping("/{id}/reviews")
    public List<Review> findAllContentReviews(@PathVariable String id) {
        return service.findAllContentReviews(id);
    }

    //GET http://localhost:8080/contents/1230-214-24153/reviews/2141-3252-14214-3
    @GetMapping("/{contentId}/reviews/{reviewId}")
    public Review findContentReview(@PathVariable String contentId, @PathVariable String reviewId) {
        return service.findContentReview(contentId, reviewId);
    }

}
