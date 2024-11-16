package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.TalkException;

import java.util.UUID;

public class TalkNotFoundException extends TalkException {
    public TalkNotFoundException(UUID id) {
        super("Talk not found with ID: " + id);
    }
}
