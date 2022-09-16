package dev.pepus.reviews.controller;

import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.model.Roles;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.ContentRepository;
import dev.pepus.reviews.repository.ReviewRepository;
import dev.pepus.reviews.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final ReviewRepository reviewRepository;
    private final ContentRepository contentRepository;
    private final ReviewController controller;

    public UserController(UserRepository repository, ReviewRepository reviewRepository,
                          ContentRepository contentRepository) {
        this.repository = repository;
        this.reviewRepository = reviewRepository;
        this.contentRepository = contentRepository;
        this.controller = new ReviewController(reviewRepository, contentRepository);
    }

    //GET http://localhost:8080/users
    @GetMapping
    public List<User> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    //GET http://localhost:8080/users/121332-23214-12-1
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    //GET http://localhost:8080/users/1231-2313/reviews
    @GetMapping("/{id}/reviews")
    public List<Review> findAllUserReviews(@PathVariable String id) {
        return repository.findById(id).get().getReviews();
    }

    //GET http://localhost:8080/users/1231-2313/reviews/24324-2102-41
    @GetMapping("/{userId}/reviews/{reviewId}")
    public Optional<Review> findUserReview(@PathVariable String userId, @PathVariable String reviewId) {
        return reviewRepository.findById(reviewId);
    }

    //POST http://localhost:8080/users
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody Map<String, String> json){
        return repository.save(new User(json.get("username"), String.valueOf(json.get("password").hashCode())));
    }

    //POST http://localhost:8080/users/22-131-23/reviews
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reviews")
    public Review createReview(@PathVariable String id, @RequestBody Map<String, String> json) {
        User usr = repository.findById(id).get();
        return controller.create(json, usr);
    }

    //PUT http://localhost:8080/users/213-123-1-24
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Map<String, String> json,@PathVariable String id) {
        try {
            User usr = repository.findById(id).get();
            String newPassword = (String.valueOf(json.get("oldPassword").hashCode()).equals(usr.getPassword())) ?
                    (String.valueOf(json.get("newPassword").hashCode())) : usr.getPassword();
            if (newPassword.equals(usr.getPassword())) throw new IllegalArgumentException("Wrong old password");
            usr.setPassword(newPassword);
            repository.save(usr);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //PUT http://localhost:8080/123-231-421/reviews/23412-13-1324
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userId}/reviews/{reviewId}")
    public void updateReview(@RequestBody Map<String, String> json, @PathVariable String userId, @PathVariable String reviewId){
        Review review = reviewRepository.findById(reviewId).get();
        int rating = Integer.parseInt(json.get("rating"));
        review.setRating((rating >= 1 && rating <= 10) ? rating : (rating > 10) ? 10 : 1);
        review.setComment(json.get("comment"));
        review.setUpdateDate(LocalDateTime.now());
        controller.update(review, reviewId);
    }

    //DELETE http://localhost:8080/users/14-1234-134
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repository.findById(id).get().getReviews().forEach(r -> controller.delete(r.getId()));
        repository.deleteById(id);
    }

    //DELETE http://localhost:8080/users/242-124-124-5-2/reviews/234-523-43
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/reviews/{reviewId}")
    public void deleteReview(@PathVariable String userId, @PathVariable String reviewId) {
        controller.delete(reviewId);
    }
}
