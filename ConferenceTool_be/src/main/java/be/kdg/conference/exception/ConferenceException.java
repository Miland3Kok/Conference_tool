package be.kdg.conference.exception;

public class ConferenceException extends RuntimeException {
    public ConferenceException(String message) {
        super("Conference exception: "+message);
    }
}
