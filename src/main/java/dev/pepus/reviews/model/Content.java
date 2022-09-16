package dev.pepus.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "contents")
public class Content {
    @Id
    private String id;
    @Column(unique = true)
    private String contentName;
    private float averageRating;
    @OneToMany(mappedBy = "content")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    public Content(String contentName) {
        id = UUID.randomUUID().toString();
        this.contentName = contentName;
        averageRating = 0;
    }

    public Content() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Float.compare(content.averageRating, averageRating) == 0 && id.equals(content.id) && contentName.equals(content.contentName) && reviews.equals(content.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentName, averageRating, reviews);
    }

    @Override
    public String toString() {
        return "Content{" +
                "id='" + id + '\'' +
                ", contentName='" + contentName + '\'' +
                ", averageRating=" + averageRating +
                ", reviews=" + reviews +
                '}';
    }

}
