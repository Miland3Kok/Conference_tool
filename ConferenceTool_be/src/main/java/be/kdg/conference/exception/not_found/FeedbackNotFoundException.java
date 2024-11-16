package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.FeedbackException;

import java.util.UUID;

public class FeedbackNotFoundException extends FeedbackException {
    public FeedbackNotFoundException(UUID id) {
        super("Feedback not found with ID: " + id);
    }
}
