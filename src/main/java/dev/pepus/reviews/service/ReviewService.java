package dev.pepus.reviews.service;

import dev.pepus.reviews.model.Content;
import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.ContentRepository;
import dev.pepus.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentService service;

    public List<Review> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Review findById(String id) {
        return repository.findById(id).get();
    }

    public Review create(Map<String, String> json, User user) {
        String contentName = json.get("contentName");
        Content content = (contentRepository.existsByContentName(contentName)) ?
                contentRepository.findContentByContentName(contentName).get() :
                contentRepository.save(new Content(contentName));
        int rating = Integer.parseInt(json.get("rating"));
        rating = ((rating >= 1 && rating <= 10) ? rating : (rating > 10) ? 10 : 1);
        Review review = new Review(user, content, rating, json.get("comment"));
        return service.createReview(review, content.getId());
    }

    public void update (Review review, String id) {
        Review rvw = repository.findById(id).get();
        rvw.setRating(review.getRating());
        rvw.setComment(review.getComment());
        rvw.setUpdateDate(review.getUpdateDate());
        service.updateReview(rvw, rvw.getContent().getId(), id);
    }

    public void delete (String id){
        service.deleteReview(findById(id).getContent().getId(), id);
    }

}
