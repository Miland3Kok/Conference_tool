package be.kdg.conference.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super("User exception: "+message);
    }
}
