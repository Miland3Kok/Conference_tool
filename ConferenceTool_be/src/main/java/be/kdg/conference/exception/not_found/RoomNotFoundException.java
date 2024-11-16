package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.RoomException;

import java.util.UUID;

public class RoomNotFoundException extends RoomException {
    public RoomNotFoundException(UUID id) {
        super("Room not found with ID: "+id);
    }
}
