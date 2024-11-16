package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.FeedbackException;

public class FeedbackAlreadyExistsException extends FeedbackException {
    public FeedbackAlreadyExistsException(String message) {
        super("Feedback already exists.");
    }
}
