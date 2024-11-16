package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.SpeakerException;

import java.util.UUID;

public class SpeakerNotFoundException extends SpeakerException {
    public SpeakerNotFoundException(UUID id) {
        super("Speaker not found with ID: " + id);
    }
}
