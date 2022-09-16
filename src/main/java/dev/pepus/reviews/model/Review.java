package dev.pepus.reviews.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "content_id")
    @JsonBackReference
    private Content content;
    private String username;
    private String contentName;
    private int rating;
    private String comment;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;


    public Review(User user, Content content, int rating, String comment) {

        id = UUID.randomUUID().toString();
        this.user = user;
        this.content = content;
        this.username = user.getUsername();
        this.contentName = content.getContentName();
        this.rating = rating;
        this.comment = comment;
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public Review() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rating == review.rating && id.equals(review.id) && user.equals(review.user) && content.equals(review.content) && username.equals(review.username) && contentName.equals(review.contentName) && comment.equals(review.comment) && creationDate.equals(review.creationDate) && updateDate.equals(review.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, content, username, contentName, rating, comment, creationDate, updateDate);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", content=" + content +
                ", username='" + username + '\'' +
                ", contentName='" + contentName + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
