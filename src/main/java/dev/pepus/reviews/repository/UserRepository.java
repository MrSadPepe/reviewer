package dev.pepus.reviews.repository;

import dev.pepus.reviews.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByUsername(String username);

    Boolean existsByUsername(String username);

}
