package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.UserException;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String message) {
        super("User already exists.");
    }
}
