package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.Content;
import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.ContentRepository;
import dev.pepus.reviews.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository repository;
    private final ContentRepository contentRepository;
    private final ContentController controller;

    public ReviewController(ReviewRepository repository, ContentRepository contentRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
        this.controller = new ContentController(repository, contentRepository);
    }

    //GET http://localhost:8080/reviews
    @GetMapping
    public List<Review> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    //GET http://localhost:8080/reviews/1242-2342-234234-234
    @GetMapping("/{id}")
    public Review findById(@PathVariable String id) {
        return repository.findById(id).get();
    }

    //POST http://localhost:8080/reviews
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Review create(@RequestBody Map<String, String> json, User user) {
        String contentName = json.get("contentName");
        Content content = (contentRepository.existsByContentName(contentName)) ?
                contentRepository.findContentByContentName(contentName).get() :
                contentRepository.save(new Content(contentName));
        int rating = Integer.parseInt(json.get("rating"));
        rating = ((rating >= 1 && rating <= 10) ? rating : (rating > 10) ? 10 : 1);
        Review review = new Review(user, content, rating, json.get("comment"));
        return controller.createReview(review, content.getId());
    }

    //PUT http://localhost:8080/reviews/1242-2342-234234-234
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update (@RequestBody Review review, @PathVariable String id) {
        Review rvw = repository.findById(id).get();
        rvw.setRating(review.getRating());
        rvw.setComment(review.getComment());
        rvw.setUpdateDate(review.getCreationDate());
        controller.updateReview(rvw, rvw.getContent().getId(), id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable String id){
        controller.deleteReview(findById(id).getContent().getId(), id);
    }

}
