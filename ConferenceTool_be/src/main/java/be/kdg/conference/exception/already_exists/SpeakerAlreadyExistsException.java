package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.SpeakerException;

public class SpeakerAlreadyExistsException extends SpeakerException {
    public SpeakerAlreadyExistsException(String message) {
        super("Speaker already exists.");
    }
}
