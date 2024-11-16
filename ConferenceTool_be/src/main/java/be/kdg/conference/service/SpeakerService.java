package be.kdg.conference.service;

import be.kdg.conference.controller.SpeakerController;
import be.kdg.conference.controller.SpeakerController.MakeSpeakerDto;
import be.kdg.conference.controller.dto.SpeakerDTO;

import java.util.List;
import java.util.UUID;

public interface SpeakerService {
    void createSpeaker(MakeSpeakerDto makeSpeakerDto);

    void updateSpeaker(SpeakerController.UpdateSpeakerDto updateSpeakerDto);

    List<SpeakerDTO> getAllSpeakers();

    SpeakerDTO getSpeakerById(UUID id);

    void deleteSpeaker(UUID speakerUUID);

    List<SpeakerDTO> getAllSpeakersByUpcomingConferenceId();

    SpeakerDTO checkIfUserIsSpeakerAndReturnSpeaker(UUID userId);

    void createSpeakerWithId(UUID uuid);

    List<UUID> getUserIdsOfSpeakers();

    boolean checkIfUserIsSpeaker(UUID uuid);

    UUID getSpeakerIdByUserId(UUID uuid);
}
