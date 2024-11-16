package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.ConferenceException;

import java.util.UUID;

public class ConferenceNotFoundException extends ConferenceException {
    public ConferenceNotFoundException(UUID id) {
        super("Conference not found with ID: " + id);
    }
}
