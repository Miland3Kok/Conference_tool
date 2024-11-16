package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.ConferenceException;

public class ConferenceAlreadyExistsException extends ConferenceException {
    public ConferenceAlreadyExistsException(String name) {
        super("Conference already exists with name: " + name);
    }
}
