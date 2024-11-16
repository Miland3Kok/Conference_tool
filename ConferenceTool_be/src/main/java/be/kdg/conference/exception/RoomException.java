package be.kdg.conference.exception;

public class RoomException extends RuntimeException {
    public RoomException(String message) {
        super("Room exception: "+message);
    }
}
