package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.LocationDTO;
import be.kdg.conference.exception.DataAccessErrorException;
import be.kdg.conference.exception.already_exists.LocationAlreadyExistsException;
import be.kdg.conference.exception.not_found.LocationNotFoundException;
import be.kdg.conference.model.eventmanagement.Location;
import be.kdg.conference.service.implementation.LocationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationServiceImpl locationService;

    public LocationController(LocationServiceImpl locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        try {
            List<Location> locations = locationService.getAllLocations();
            if (locations.isEmpty()) {
                log.info("No locations yet.");
                return ResponseEntity.noContent().build();
            }
            log.info("Locations retrieved successfully.");
            return ResponseEntity.ok(locations);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error retrieving locations: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        log.info("Creating location...");
        try {
            LocationDTO location = locationService.createLocation(locationDTO);
            log.info("Location created successfully.");
            return ResponseEntity.ok(location);
        } catch (LocationAlreadyExistsException | DataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error creating location: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable UUID id) {
        log.info("Retrieving location with id: {}", id);
        try {
            Location location = locationService.getLocationById(id);
            log.info("Location retrieved successfully.");
            return ResponseEntity.ok(location);
        } catch (LocationNotFoundException | DataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
        try {
            log.info("Updating location...");
            log.info("locationDTO: {}", locationDTO);
            LocationDTO updatedLocation = locationService.updateLocation(locationDTO);
            log.info("Location updated successfully");
            return ResponseEntity.ok(updatedLocation);
        } catch (LocationNotFoundException | DataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating location: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
