package be.kdg.conference.service;

import be.kdg.conference.controller.dto.LocationDTO;
import be.kdg.conference.exception.LocationException;
import be.kdg.conference.model.eventmanagement.Location;

import java.util.List;
import java.util.UUID;

public interface LocationService {
    Location getLocationById(UUID locationId) throws LocationException;
    LocationDTO createLocation(LocationDTO locationDTO);
    List<Location> getAllLocations();

    LocationDTO updateLocation(LocationDTO locationDTO);
}
