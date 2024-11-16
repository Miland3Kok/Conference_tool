package be.kdg.conference.exception;

public class TalkException extends RuntimeException {
    public TalkException(String message) {
        super("Talk exception: "+message);
    }
}
