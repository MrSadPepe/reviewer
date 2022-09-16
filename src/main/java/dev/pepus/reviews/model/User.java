package dev.pepus.reviews.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();
    @Enumerated(EnumType.ORDINAL)
    private Roles role;

    public User(String username, String password) {
        id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        role = Roles.PEASANT;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && password.equals(user.password) && reviews.equals(user.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, reviews);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
