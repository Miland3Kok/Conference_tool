package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.UserException;

import java.util.UUID;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(UUID id) {
        super("User not found with ID: " + id);
    }
}
