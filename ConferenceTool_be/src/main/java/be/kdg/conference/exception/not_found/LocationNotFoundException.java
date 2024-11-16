package be.kdg.conference.exception.not_found;

import be.kdg.conference.exception.LocationException;

import java.util.UUID;

public class LocationNotFoundException extends LocationException {
    public LocationNotFoundException(UUID id) {
        super("Location not found with ID: " + id);
    }
}
