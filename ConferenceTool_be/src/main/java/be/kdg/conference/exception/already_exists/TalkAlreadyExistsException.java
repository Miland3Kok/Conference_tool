package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.TalkException;

public class TalkAlreadyExistsException extends TalkException {
    public TalkAlreadyExistsException(String message) {
        super("Talk already exists.");
    }
}
