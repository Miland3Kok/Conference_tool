package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.RoomException;

public class RoomAlreadyExistsException extends RoomException {
    public RoomAlreadyExistsException(String message) {
        super("Room already exists.");
    }
}
