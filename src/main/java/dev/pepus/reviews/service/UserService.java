package dev.pepus.reviews.service;

import dev.pepus.reviews.model.Review;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.ContentRepository;
import dev.pepus.reviews.repository.ReviewRepository;
import dev.pepus.reviews.repository.RoleRepository;
import dev.pepus.reviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository repository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService service;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username).get();

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getGrantedAuthority(user));
    }


    private Collection<GrantedAuthority> getGrantedAuthority(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole().getName().equalsIgnoreCase("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_PEASANT"));
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
        return list;
    }

    public User findById(String id) {
        return repository.findById(id).get();
    }

    public User findByUsername(String username) {
        return repository.findUserByUsername(username).get();
    }

    public List<Review> findAllUserReviews(String id) {
        return repository.findById(id).get().getReviews();
    }

    public Review findUserReview(String userId, String reviewId) {
        return reviewRepository.findById(reviewId).get();
    }

    public User create(Map<String, String> json){
        return repository.save(new User(json.get("username"), bCryptPasswordEncoder.encode(json.get("password"))));
    }

    public Review createReview(String id, Map<String, String> json) {
        User usr = repository.findById(id).get();
        return service.create(json, usr);
    }

    public void update(Map<String, String> json, String id) {
        try {
            User usr = repository.findById(id).get();
            String newPassword = (bCryptPasswordEncoder.matches(json.get("oldPassword"), usr.getPassword())) ?
                    (bCryptPasswordEncoder.encode(json.get("newPassword"))) : usr.getPassword();
            if (newPassword.equals(usr.getPassword())) throw new IllegalArgumentException("Wrong old password");
            usr.setPassword(newPassword);
            repository.save(usr);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateReview(Map<String, String> json, String userId, String reviewId){
        Review review = reviewRepository.findById(reviewId).get();
        int rating = Integer.parseInt(json.get("rating"));
        review.setRating((rating >= 1 && rating <= 10) ? rating : (rating > 10) ? 10 : 1);
        review.setComment(json.get("comment"));
        review.setUpdateDate(LocalDateTime.now());
        service.update(review, reviewId);
    }

    public void delete(String id) {
        repository.findById(id).get().getReviews().forEach(r -> service.delete(r.getId()));
        repository.deleteById(id);
    }

    public void deleteReview(String userId, String reviewId) {
        service.delete(reviewId);
    }
}
