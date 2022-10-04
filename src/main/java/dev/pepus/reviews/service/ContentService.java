package dev.pepus.reviews.service;

import dev.pepus.reviews.model.Content;
import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.repository.ContentRepository;
import dev.pepus.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContentService {
    @Autowired
    private ContentRepository repository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Content> findAll(){
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Content findById(@PathVariable String id){
        return repository.findById(id).get();
    }

    public List<Review> findAllContentReviews(@PathVariable String id) {
        return repository.findById(id).get().getReviews();
    }

    public Review findContentReview(@PathVariable String contentId, @PathVariable String reviewId) {
        return reviewRepository.findById(reviewId).get();
    }

    public Content create(@RequestBody Content content) {
        return repository.save(content);
    }

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

    public void update(@PathVariable String id) {
        Content cntnt = repository.findById(id).get();
        List<Review> reviews = StreamSupport.stream(reviewRepository.findReviewsByContentId(id).spliterator(),
                false).toList();
        float rating = ((float) (reviews.stream().mapToInt(Review::getRating).sum()) /
                ((float) (reviews.size())));
        cntnt.setAverageRating(rating);
        repository.save(cntnt);
    }

    public void updateReview(@RequestBody Review review, @PathVariable String contentId, @PathVariable String reviewId) {
        Review rvw = reviewRepository.findById(reviewId).get();
        rvw.setRating(review.getRating());
        rvw.setComment(review.getComment());
        rvw.setUpdateDate(review.getUpdateDate());
        reviewRepository.save(rvw);
        update(contentId);
    }

    public void delete(@PathVariable String id) {
        repository.findById(id).get().getReviews().forEach(r -> reviewRepository.deleteById(r.getId()));
        repository.deleteById(id);
    }

    public void deleteReview(@PathVariable String contentId, @PathVariable String reviewId) {
        reviewRepository.deleteById(reviewId);
        update(contentId);
    }
}
