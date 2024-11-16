package be.kdg.conference.exception;

public class QuestionException extends RuntimeException {
    public QuestionException(String message) {
        super("Question exception: " + message);
    }
}
