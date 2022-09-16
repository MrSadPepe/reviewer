package dev.pepus.reviews.repository;

import dev.pepus.reviews.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, String> {

    Iterable<Review> findReviewsByUserId(String userId);

    Iterable<Review> findReviewsByContentId(String contentId);

}
