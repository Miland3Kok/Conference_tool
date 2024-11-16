package be.kdg.conference.service;

import be.kdg.conference.controller.dto.ConferenceDTO;
import be.kdg.conference.controller.dto.ConferenceFloorPlanDTO;
import be.kdg.conference.exception.LocationException;
import be.kdg.conference.model.eventmanagement.Conference;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ConferenceService {
    void createConference(ConferenceDTO conferenceDTO);

    List<ConferenceDTO> getAllConferences();

    void deleteConference(UUID conferenceUUID);

    void updateConference(ConferenceDTO conferenceDTO);

    ConferenceDTO getLatestConference();

    Conference getConferenceById(UUID id);

    Conference convertConverenceDTOToConference(ConferenceDTO conferenceDTO) throws LocationException;

    ConferenceDTO getConferenceDTOById(UUID id);

    void toggleConferenceStatus(UUID id);

    Conference getActiveConferenceAndIdNot(UUID selfId);

    ConferenceFloorPlanDTO getActiveConference();

    void saveFloorplanImage(MultipartFile file, UUID conferenceId);


}
