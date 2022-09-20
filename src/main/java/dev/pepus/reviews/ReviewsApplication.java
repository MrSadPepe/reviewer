package dev.pepus.reviews;

import dev.pepus.reviews.model.Role;
import dev.pepus.reviews.model.User;
import dev.pepus.reviews.repository.RoleRepository;
import dev.pepus.reviews.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class ReviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewsApplication.class, args);
    }

}
