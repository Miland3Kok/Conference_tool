package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.QuestionException;

import java.util.UUID;

public class QuestionNotFoundException extends QuestionException {
    public QuestionNotFoundException(UUID id) {
        super("Question not found with ID: " + id);
    }
}
