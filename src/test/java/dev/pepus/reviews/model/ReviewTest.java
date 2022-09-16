package dev.pepus.reviews.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {

    @Test
    void create_new_review() {
        Review review = new Review(new User("Pepu5", "kekekaka"), new Content("Stuart Little"), 10, "ONE OF MOVIES EVER!!!");

        assertNotNull(review);
        assertEquals("Stuart Little", review.getContentName());
        assertTrue(review.getClass().isRecord());
        assertEquals(9, review.getClass().getRecordComponents().length);
    }


}
