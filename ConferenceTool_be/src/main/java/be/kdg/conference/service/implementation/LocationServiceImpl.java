package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.LocationDTO;
import be.kdg.conference.exception.DataAccessErrorException;
import be.kdg.conference.exception.already_exists.LocationAlreadyExistsException;
import be.kdg.conference.exception.not_found.LocationNotFoundException;
import be.kdg.conference.model.eventmanagement.Location;
import be.kdg.conference.repository.LocationRepository;
import be.kdg.conference.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getLocationById(UUID locationId) {
        try {
            return locationRepository.findById(locationId)
                    .orElseThrow(() -> new LocationNotFoundException(locationId));
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error getting location by ID", e);
        }
    }

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO){
        try {
            if (locationRepository.existsByCityAndStreetAndNumber(locationDTO.getCity(), locationDTO.getStreet(), locationDTO.getNumber())) {
                throw new LocationAlreadyExistsException();
            }
            Location saved = locationRepository.save(new Location(locationDTO));
            return new LocationDTO(saved);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error creating location", e);
        }
    }

    @Override
    public List<Location> getAllLocations() {
        try {
            return locationRepository.findAll();
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error getting all locations", e);
        }
    }

    @Override
    public LocationDTO updateLocation(LocationDTO locationDTO) throws LocationNotFoundException, DataAccessErrorException {
        try {
            Location location = locationRepository.findById(locationDTO.getLocation_id())
                    .orElseThrow(() -> new LocationNotFoundException(locationDTO.getLocation_id()));
            location.setStreet(locationDTO.getStreet());
            location.setNumber(locationDTO.getNumber());
            location.setCity(locationDTO.getCity());
            location.setPostal_code(locationDTO.getPostal_code());
            location.setCountry(locationDTO.getCountry());
            location.setExtraInfo(locationDTO.getExtraInfo());
            Location savedLocation = locationRepository.save(location);
            return new LocationDTO(savedLocation);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error updating location", e);
        }
    }
}
