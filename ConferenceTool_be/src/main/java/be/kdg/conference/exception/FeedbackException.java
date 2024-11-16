package be.kdg.conference.exception;

public class FeedbackException extends RuntimeException {
    public FeedbackException(String message) {
        super("Feedback exception: "+message);
    }
}
