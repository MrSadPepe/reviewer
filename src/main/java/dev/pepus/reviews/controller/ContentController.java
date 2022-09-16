package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.Content;
import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.repository.ContentRepository;
import dev.pepus.reviews.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/contents")
public class ContentController {
    private final ContentRepository repository;
    private final ReviewRepository reviewRepository;

    public ContentController(ReviewRepository reviewRepository, ContentRepository repository) {
        this.repository = repository;
        this.reviewRepository = reviewRepository;
    }

    //GET http://localhost:8080/contents
    @GetMapping
    public List<Content> findAll(){
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    //GET http://localhost:8080/contents/123-123-2
    @GetMapping("/{id}")
    public Optional<Content> findById(@PathVariable String id){
        return repository.findById(id);
    }

    //GET http://localhost:8080/contents/123-2141-24-123/reviews
    @GetMapping("/{id}/reviews")
    public List<Review> findAllContentReviews(@PathVariable String id) {
        return repository.findById(id).get().getReviews();
    }

    //GET http://localhost:8080/contents/1230-214-24153/reviews/2141-3252-14214-3
    @GetMapping("/{contentId}/reviews/{reviewId}")
    public Optional<Review> findContentReview(@PathVariable String contentId, @PathVariable String reviewId) {
        return reviewRepository.findById(reviewId);
    }

    //POST http://localhost:8080/contents
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Content create(@RequestBody Content content) {
        return repository.save(content);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reviews")
    public Review createReview(@RequestBody Review review, @PathVariable String id) {
        try {
            if (StreamSupport.stream(reviewRepository.findReviewsByContentId(id).spliterator(),
                    false).anyMatch(r -> r.getUser().getId().equals(review.getUser().getId()))) {
                throw new IllegalArgumentException();
            }
            reviewRepository.save(review);
            update(id);
            return review;
        }
        catch (IllegalArgumentException e) {
            System.out.println("There is already review from this user on this content");
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable String id) {
        Content cntnt = repository.findById(id).get();
        List<Review> reviews = StreamSupport.stream(reviewRepository.findReviewsByContentId(id).spliterator(),
                false).toList();
        float rating = ((float) (reviews.stream().mapToInt(Review::getRating).sum()) /
                ((float) (reviews.size())));
        cntnt.setAverageRating(rating);
        repository.save(cntnt);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{contentId}/reviews/{reviewId}")
    public void updateReview(@RequestBody Review review, @PathVariable String contentId, @PathVariable String reviewId) {
        Review rvw = reviewRepository.findById(reviewId).get();
        rvw.setRating(review.getRating());
        rvw.setComment(review.getComment());
        rvw.setUpdateDate(review.getCreationDate());
        reviewRepository.save(rvw);
        update(contentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repository.findById(id).get().getReviews().forEach(r -> reviewRepository.deleteById(r.getId()));
        repository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{contentId}/Reviews/{reviewId}")
    public void deleteReview(@PathVariable String contentId, @PathVariable String reviewId) {
        reviewRepository.deleteById(reviewId);
        update(contentId);
    }
}
