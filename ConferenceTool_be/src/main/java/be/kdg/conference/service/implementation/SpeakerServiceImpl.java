package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.SpeakerController.MakeSpeakerDto;
import be.kdg.conference.controller.SpeakerController.UpdateSpeakerDto;
import be.kdg.conference.controller.dto.SpeakerDTO;
import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Speaker;
import be.kdg.conference.repository.ConferenceRepository;
import be.kdg.conference.repository.SpeakerRepository;
import be.kdg.conference.repository.UserRepository;
import be.kdg.conference.service.SpeakerService;
import be.kdg.conference.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    private final UserRepository userRepository; // veranderen naar UserService

    private final UserService userService;
    private final SpeakerRepository speakerRepository;

    private final ConferenceRepository conferenceRepository;

    public SpeakerServiceImpl(UserRepository userRepository,UserService userService, SpeakerRepository speakerRepository, ConferenceRepository conferenceRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.speakerRepository = speakerRepository;
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public void createSpeaker(MakeSpeakerDto makeSpeakerDto) {
        User user = userRepository.findById(makeSpeakerDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Speaker speaker = new Speaker(user, makeSpeakerDto.getBio(), makeSpeakerDto.getPhone());
        speakerRepository.save(speaker);
    }

    @Transactional
    @Override
    public void updateSpeaker(UpdateSpeakerDto updateSpeakerDto) {
        Speaker speaker = speakerRepository.findById(updateSpeakerDto.getSpeakerId())
                .orElseThrow(() -> new IllegalArgumentException("Speaker not found"));

        if (!updateSpeakerDto.getBio().isEmpty()){
            speaker.setBio(updateSpeakerDto.getBio());
        }
        if (!updateSpeakerDto.getPhone().isEmpty()){
            speaker.setPhone(updateSpeakerDto.getPhone());
        }
        speakerRepository.save(speaker);
    }

    @Transactional
    @Override
    public List<SpeakerDTO> getAllSpeakers() {
        return speakerRepository.findAll().stream()
                .map(SpeakerDTO::new)
                .toList();
    }

    @Transactional
    @Override
    public SpeakerDTO getSpeakerById(UUID id) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Speaker not found"));
        return new SpeakerDTO(speaker);
    }

    @Override
    public void deleteSpeaker(UUID speakerUUID) {
        speakerRepository.deleteById(speakerUUID);
    }

    @Override
    public List<SpeakerDTO> getAllSpeakersByUpcomingConferenceId() {
        Optional<Conference> conference = conferenceRepository.findByActiveTrue();
        if (conference.isEmpty()) {
            throw new IllegalArgumentException("No active conference found");
        } else {
            return speakerRepository.findSpeakersByConferenceId(conference.get().getConference_id())
                    .stream()
                    .map(SpeakerDTO::new)
                    .toList();
        }

    }

    @Override
    public SpeakerDTO checkIfUserIsSpeakerAndReturnSpeaker(UUID userId) {
        User user = userService.getUserById(userId);
        Optional<Speaker> speaker = speakerRepository.findByUser(user);
        if (speaker.isEmpty()) {
            throw new IllegalArgumentException("User is not a speaker");
        } else {
            return new SpeakerDTO(speaker.get());
        }
    }

    @Override
    public void createSpeakerWithId(UUID uuid) {
        User user = userService.getUserById(uuid);
        Speaker speaker = new Speaker(user, "", "");
        speakerRepository.save(speaker);
    }

    @Override
    public List<UUID> getUserIdsOfSpeakers() {
        return speakerRepository.findAll()
                .stream()
                .map(Speaker::getUser)
                .map(User::getUser_id)
                .toList();
    }

    @Override
    public boolean checkIfUserIsSpeaker(UUID uuid) {
        User user = userService.getUserById(uuid);
        return speakerRepository.findByUser(user).isPresent();
    }

    @Override
    public UUID getSpeakerIdByUserId(UUID uuid) {
        User user = userService.getUserById(uuid);
        return speakerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Speaker not found"))
                .getSpeaker_id();
    }
}
