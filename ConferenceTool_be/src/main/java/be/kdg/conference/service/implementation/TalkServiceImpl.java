package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.AddSpeakerToTalkDTO;
import be.kdg.conference.controller.dto.MakeTalkDTO;
import be.kdg.conference.controller.dto.TalkDTO;
import be.kdg.conference.controller.dto.UpdateTalkDTO;
import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.*;
import be.kdg.conference.repository.SpeakerRepository;
import be.kdg.conference.repository.TalkRepository;
import be.kdg.conference.service.FavouriteTalkService;
import be.kdg.conference.service.TalkService;
import be.kdg.conference.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.function.Add;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TalkServiceImpl implements TalkService {

    private final SpeakerRepository speakerRepository;

    private final RoomServiceImpl roomService;

    private final TalkRepository talkRepository;

    private final UserService userService;

    private final FavouriteTalkService favouriteTalkService;

    private final ConferenceServiceImpl conferenceService;

    public TalkServiceImpl(
                           TalkRepository talkRepository,
                           SpeakerRepository speakerRepository,
                           RoomServiceImpl roomService,
                           UserService userService,
                           FavouriteTalkService favouriteTalkService,
                           ConferenceServiceImpl conferenceService) {
        this.talkRepository = talkRepository;
        this.speakerRepository = speakerRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.favouriteTalkService = favouriteTalkService;
        this.conferenceService = conferenceService;
    }

    @Override
    @Transactional
    public void createTalk(MakeTalkDTO makeTalkDTO) {
        Room room = roomService.getRoomById(makeTalkDTO.getRoom());

        Talk talk = new Talk(
                makeTalkDTO.getTitle(),
                makeTalkDTO.getDescription(),
                makeTalkDTO.getStart_date(),
                makeTalkDTO.getEnd_date(),
                room
                );

        List<Speaker> speakers = getSpeakersFromUUIDList(makeTalkDTO.getSpeakers_id());
        log.info("Speakers: {}", speakers);
        talk.setSpeakers(speakers);

        talkRepository.save(talk);
    }

    @Override
    @Transactional
    public TalkDTO getTalk(UUID id) {
        Talk talk = talkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Talk with id: " + id + " not found!"));

        Hibernate.initialize(talk.getSpeakers());
        return new TalkDTO(talk);
    }

    @Override
    @Transactional
    public void updateTalk(UpdateTalkDTO updateTalkDTO) {
        Talk talk = talkRepository.findById(updateTalkDTO.getTalk_id())
                .orElseThrow(() -> new IllegalArgumentException("Talk with id: " + updateTalkDTO.getTalk_id() + " not found!"));
        talk.setTitle(updateTalkDTO.getTitle());
        talk.setDescription(updateTalkDTO.getDescription());
        talk.setStart_date(LocalDateTime.parse(updateTalkDTO.getStart_date()));
        talk.setEnd_date(LocalDateTime.parse(updateTalkDTO.getEnd_date()));
        talkRepository.save(talk);
    }

    @Override
    public void deleteTalk(UUID id) {
        talkRepository.deleteById(id);
    }

    @Override
    public Talk getTalkById(String talkId) {
        return talkRepository.findById(UUID.fromString(talkId)).orElseThrow(() -> new IllegalArgumentException("Talk with id: " + talkId + " not found!"));
    }

    @Override
    @Transactional
    public void addSpeakerToTalk(AddSpeakerToTalkDTO addSpeakerToTalkDTO) {
        updateSpeakerInTalk(addSpeakerToTalkDTO.getTalkId(), addSpeakerToTalkDTO.getSpeakerId(), true);
    }

    @Override
    @Transactional
    public void removeSpeakerFromTalk(AddSpeakerToTalkDTO addSpeakerToTalkDTO) {
        updateSpeakerInTalk(addSpeakerToTalkDTO.getTalkId(), addSpeakerToTalkDTO.getSpeakerId(), false);
    }

    @Override
    @Transactional
    public void updateConference(UUID talkId, UUID conferenceId) {
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() -> new IllegalArgumentException("Talk with id: " + talkId + " not found!"));

        talkRepository.save(talk);
    }

    @Override
    public List<TalkDTO> getAllTalksForConference(UUID id) {
        List<Talk> talks = talkRepository.findAllByConferenceId(id);
        return talks.stream()
                .map(TalkDTO::new)
                .sorted(Comparator.comparing(TalkDTO::getStart_date))
                .collect(Collectors.toList());
    }


    private void updateSpeakerInTalk(UUID talkId, UUID speakerId, boolean add) {
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() -> new IllegalArgumentException("Talk with id: " + talkId + " not found!"));
        Speaker speaker = speakerRepository.findById(speakerId)
                .orElseThrow(() -> new IllegalArgumentException("Speaker with id: " + speakerId + " not found!"));

        if (add) {
            talk.getSpeakers().add(speaker);
        } else {
            talk.getSpeakers().remove(speaker);
        }

        talkRepository.save(talk);
    }

    private List<Speaker> getSpeakersFromUUIDList(List<UUID> speakersId) {
        return speakersId.stream()
                .map(speakerRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


    @Override
    public void addFavouriteTalk(UUID userId, UUID talkId) {
        User user = userService.getUserById(userId);
        Talk talk = talkRepository.findById(talkId).orElseThrow(() -> new IllegalArgumentException("Talk not found"));
        FavouriteTalk favouriteTalk = new FavouriteTalk(user, talk);
        favouriteTalkService.saveFavouriteTalk(favouriteTalk);
    }

    @Override
    public void removeFavouriteTalk(UUID userId, UUID talkId) {
        User user = userService.getUserById(userId);
        Talk talk = talkRepository.findById(talkId).orElseThrow(() -> new IllegalArgumentException("Talk not found"));
        FavouriteTalk favouriteTalk = favouriteTalkService.findByUserAndTalk(user, talk).orElseThrow(() -> new IllegalArgumentException("Favourite talk not found"));
        favouriteTalkService.delete(favouriteTalk);
    }

    @Override
    public List<TalkDTO> getFavouriteTalksByUserId(UUID userId) {
        User user = userService.getUserById(userId);
        return user.getFavouriteTalks().stream()
                .map(favouriteTalk -> new TalkDTO(favouriteTalk.getTalk()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TalkDTO> getFavouriteTalksByUserIdAndConferenceId(UUID userId, UUID conferenceId) {
        User user = userService.getUserById(userId);
        return user.getFavouriteTalks().stream()
                .map(FavouriteTalk::getTalk)
                .filter(talk -> talk.getRoom().getConference().getConference_id().equals(conferenceId))
                .map(TalkDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public int getAmountOfFavorites(UUID talkId) {
        return favouriteTalkService.getAmountOfFavorites(talkId);
    }

    @Override
    public List<TalkDTO> getTalksBySpeakerId(UUID speakerId) {
        Speaker speaker = speakerRepository.findById(speakerId)
                .orElseThrow(() -> new IllegalArgumentException("Speaker with id: " + speakerId + " not found!"));

        return talkRepository.findAll().stream()
                .filter(talk -> talk.getSpeakers().contains(speaker))
                .map(TalkDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<TalkDTO> getFeaturedTalks(UUID conferenceId) {
        List<Talk> talks = talkRepository.findAllByConferenceId(conferenceId);
        Collections.shuffle(talks);
        return talks.stream()
                .map(TalkDTO::new)
                .limit(5)
                .collect(Collectors.toList());
    }
}
