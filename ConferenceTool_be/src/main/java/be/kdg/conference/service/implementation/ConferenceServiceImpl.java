package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.ConferenceDTO;
import be.kdg.conference.controller.dto.ConferenceFloorPlanDTO;
import be.kdg.conference.exception.ConferenceException;
import be.kdg.conference.exception.DataAccessErrorException;
import be.kdg.conference.exception.already_exists.ConferenceAlreadyExistsException;
import be.kdg.conference.exception.not_found.ConferenceNotFoundException;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Location;
import be.kdg.conference.repository.ConferenceRepository;
import be.kdg.conference.service.ConferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final LocationServiceImpl locationService;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, LocationServiceImpl locationService) {
        this.conferenceRepository = conferenceRepository;
        this.locationService = locationService;
    }

    public void createConference(ConferenceDTO conferenceDTO) {
        try {

            if (conferenceRepository.existsByName(conferenceDTO.getName())) {
                throw new ConferenceAlreadyExistsException(conferenceDTO.getName());
            }

            Conference conference = new Conference(
                    conferenceDTO.getName(),
                    conferenceDTO.getDescription(),
                    conferenceDTO.getStart_date(),
                    conferenceDTO.getEnd_date(),
                    locationService.getLocationById(conferenceDTO.getLocation_id()),
                    conferenceDTO.isActive()
            );
            conferenceRepository.save(conference);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while creating conference", e);
        } catch (Exception e) {
            throw new ConferenceException("An unexpected error occurred while creating conference");
        }
    }

    public List<ConferenceDTO> getAllConferences() {
        try {
            return conferenceRepository.findAll().stream().map(ConferenceDTO::new).toList();
        } catch (Exception e) {
            throw new ConferenceException("Could not retrieve conferences");
        }
    }

    public ConferenceDTO getLatestConference() {
        try {
            return new ConferenceDTO(conferenceRepository.findAll().stream().findFirst().orElseThrow());
        } catch (Exception e) {
            throw new ConferenceException("Could not retrieve latest conference");
        }
    }

    @Override
    public Conference getConferenceById(UUID id) {
        Optional<Conference> conferenceOptional;
        try {
            conferenceOptional = conferenceRepository.findById(id);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while retrieving conference by ID", e);
        }
        return conferenceOptional.orElseThrow(() -> new ConferenceNotFoundException(id));
    }

    @Override
    public ConferenceDTO getConferenceDTOById(UUID id) {
        Optional<Conference> conferenceOptional;
        try {
            conferenceOptional = conferenceRepository.findById(id);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while retrieving conference DTO by ID", e);
        }
        return conferenceOptional.map(ConferenceDTO::new).orElseThrow(() -> new ConferenceNotFoundException(id));
    }

    public void deleteConference(UUID conferenceUUID) {
        try {
            conferenceRepository.deleteById(conferenceUUID);
        } catch (EmptyResultDataAccessException e) {
            throw new DataAccessErrorException("Data access exception: Conference not found for deletion", e);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while deleting conference", e);
        } catch (Exception e) {
            throw new ConferenceException("An unexpected error occurred while deleting conference");
        }
    }

    public void updateConference(ConferenceDTO conferenceDTO) {
        try {
            Conference conference = getConferenceById(conferenceDTO.getConference_id());
            if (conference != null) {
                conference.setName(conferenceDTO.getName());
                conference.setDescription(conferenceDTO.getDescription());
                conference.setStart_date(LocalDateTime.parse(conferenceDTO.getStart_date()));
                conference.setEnd_date(LocalDateTime.parse(conferenceDTO.getEnd_date()));
                Location location = locationService.getLocationById(conferenceDTO.getLocation_id());
                conference.setLocation(location);
                conferenceRepository.save(conference);
            } else {
                throw new ConferenceNotFoundException(conferenceDTO.getConference_id());
            }
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while updating conference", e);
        } catch (Exception e) {
            throw new ConferenceException("An unexpected error occurred while updating conference");
        }
    }

    public Conference convertConverenceDTOToConference(ConferenceDTO conferenceDTO) {
        try {
            Location location = locationService.getLocationById(conferenceDTO.getLocation_id());
            return new Conference(
                    conferenceDTO.getConference_id(),
                    conferenceDTO.getName(),
                    conferenceDTO.getDescription(),
                    LocalDateTime.parse(conferenceDTO.getStart_date()),
                    LocalDateTime.parse(conferenceDTO.getEnd_date()),
                    location,
                    conferenceDTO.isActive()
            );
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while converting conference DTO to conference", e);
        }
    }

    @Override
    public ConferenceFloorPlanDTO getActiveConference() {
        Optional<Conference> conferenceOptional;
        try {
            conferenceOptional = conferenceRepository.findByActiveTrue();
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while retrieving active conference", e);
        }
        return conferenceOptional.map(ConferenceFloorPlanDTO::new).orElseThrow(() -> new ConferenceException("No active conference found!"));
    }

    @Override
    public void toggleConferenceStatus(UUID id) {
        Conference conference = getConferenceById(id);
        if (conference != null) {
            Conference activeConference = getActiveConferenceAndIdNot(id);
            if (activeConference != null) {
                activeConference.setActive(false);
                conferenceRepository.save(activeConference);
                log.info("Deactivated previous active conference (" + activeConference.getName() + ")");
            }
            conference.setActive(!conference.isActive());
            conferenceRepository.save(conference);
        }
    }

    @Override
    public Conference getActiveConferenceAndIdNot(UUID selfId) {
        Optional<Conference> conferenceOptional;
        try {
            conferenceOptional = conferenceRepository.findByActiveTrueAndIdNot(selfId);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception occurred while retrieving active conference (excluding self)", e);
        }
        return conferenceOptional.orElse(null);
    }

    @Override
    public void saveFloorplanImage(MultipartFile file, UUID conferenceId) {
        try {
            Conference conference = getConferenceById(conferenceId);
            conference.setFloorPlanImage(file.getBytes());
            conferenceRepository.save(conference);
        } catch (Exception e) {
            throw new ConferenceException("Could not save floorplan image");
        }
    }

}
