package be.kdg.conference.exception.already_exists;

import be.kdg.conference.exception.LocationException;

public class LocationAlreadyExistsException extends LocationException {
    public LocationAlreadyExistsException() {
        super("Location already exists.");
    }
}
