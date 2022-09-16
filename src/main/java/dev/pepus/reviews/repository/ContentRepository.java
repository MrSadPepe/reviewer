package dev.pepus.reviews.repository;

import dev.pepus.reviews.model.Content;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContentRepository extends CrudRepository<Content, String> {

    Optional<Content> findContentByContentName(String contentName);

    Boolean existsByContentName(String contentName);

}
