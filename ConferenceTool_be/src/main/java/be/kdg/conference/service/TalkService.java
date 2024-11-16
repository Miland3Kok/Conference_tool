package be.kdg.conference.service;

import be.kdg.conference.controller.TalkController.*;
import be.kdg.conference.controller.dto.AddSpeakerToTalkDTO;
import be.kdg.conference.controller.dto.MakeTalkDTO;
import be.kdg.conference.controller.dto.TalkDTO;
import be.kdg.conference.controller.dto.UpdateTalkDTO;
import be.kdg.conference.model.eventmanagement.Talk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TalkService {

    void createTalk(MakeTalkDTO makeTalkDTO);

    //List<TalkDTO> getAllTalksForConference(UUID conferenceId);

    TalkDTO getTalk(UUID id);

    void updateTalk(UpdateTalkDTO updateTalkDTO);

    void deleteTalk(UUID id);

    Talk getTalkById(String talkId);

    void addSpeakerToTalk(AddSpeakerToTalkDTO addSpeakerToTalkDTO);

    void removeSpeakerFromTalk(AddSpeakerToTalkDTO addSpeakerToTalkDTO);

    void updateConference(UUID talkId, UUID conferenceId);

    List<TalkDTO> getAllTalksForConference(UUID id);

    void addFavouriteTalk(UUID userId, UUID talkId);

    void removeFavouriteTalk(UUID userId, UUID talkId);

    List<TalkDTO> getFavouriteTalksByUserId(UUID userId);

    int getAmountOfFavorites(UUID talkId);

    List<TalkDTO> getFavouriteTalksByUserIdAndConferenceId(UUID userId, UUID conferenceId);

    List<TalkDTO> getTalksBySpeakerId(UUID speakerId);

    List<TalkDTO> getFeaturedTalks(UUID conferenceId);
}
