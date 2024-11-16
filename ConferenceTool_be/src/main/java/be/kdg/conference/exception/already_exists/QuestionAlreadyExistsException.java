package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.QuestionException;

public class QuestionAlreadyExistsException extends QuestionException {
    public QuestionAlreadyExistsException(String message) {
        super("Question already exists.");
    }
}
