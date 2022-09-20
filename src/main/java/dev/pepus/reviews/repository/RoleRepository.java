package dev.pepus.reviews.repository;

import dev.pepus.reviews.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
