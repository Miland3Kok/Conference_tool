package be.kdg.conference.model.event_interaction;

import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.Talk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID feedback_id;

    @Column(nullable = false)
    private int rating;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Talk talk;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be between 1 and 5");
        this.rating = rating;
    }
}
