package be.kdg.conference.exception;

public class LocationException extends RuntimeException {
    public LocationException(String message) {
        super("Location exception: "+message);
    }
}
