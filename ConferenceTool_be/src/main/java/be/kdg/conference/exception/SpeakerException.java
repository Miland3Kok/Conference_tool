package be.kdg.conference.exception;

public class SpeakerException extends RuntimeException {
    public SpeakerException(String message) {
        super("Speaker exception: "+message);
    }
}
