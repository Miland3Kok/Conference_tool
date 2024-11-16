package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.SpeakerDTO;
import be.kdg.conference.service.SpeakerService;
import be.kdg.conference.service.implementation.SpeakerServiceImpl;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/speaker")
public class SpeakerController {

    private final SpeakerService speakerService;

    public SpeakerController(SpeakerServiceImpl speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SpeakerDTO>> getAllSpeakers() {
        try {
            log.info("All speakers retrieved!");
            return ResponseEntity.ok(speakerService.getAllSpeakers());
        } catch (Exception e) {
            log.warn("Failed to retrieve all speakers!", e);
            throw new RuntimeException("Failed to retrieve all speakers", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeakerDTO> getSpeakerById(@PathVariable UUID id) {
        try {
            log.info("Speaker retrieved!");
            return ResponseEntity.ok(speakerService.getSpeakerById(id));
        } catch (Exception e) {
            log.warn("Failed to retrieve speaker!", e);
            throw new RuntimeException("Failed to retrieve speaker", e);
        }
    }

    @PostMapping("/create")
    public void createSpeaker(@RequestBody @NotNull MakeSpeakerDto makeSpeakerDto) {
        try {
            speakerService.createSpeaker(makeSpeakerDto);
            log.info("Speaker created!");
        } catch (Exception e) {
            log.warn("Failed to create speaker!", e);
            throw new RuntimeException("Failed to create speaker", e);
        }
    }

    @PostMapping("/createSpeakerWithId")
    public void createSpeakerWithId(@RequestParam String userId) {
        try {
            speakerService.createSpeakerWithId(UUID.fromString(userId));
            log.info("Speaker created!");
        } catch (Exception e) {
            log.warn("Failed to create speaker!", e);
            throw new RuntimeException("Failed to create speaker", e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSpeaker(@RequestBody @NotNull UpdateSpeakerDto updateSpeakerDto) {
        try {
            speakerService.updateSpeaker(updateSpeakerDto);
            log.info("Speaker updated!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to update speaker!", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete")
    public void deleteSpeaker(@RequestParam UUID speakerUUID) {
        try {
            speakerService.deleteSpeaker(speakerUUID);
            log.info("Speaker deleted!");
        } catch (Exception e) {
            log.warn("Failed to delete speaker!", e);
            throw new RuntimeException("Failed to delete speaker", e);
        }
    }

    @RequestMapping("/upcomingConference")
    public List<SpeakerDTO> getAllSpeakersFromUpcomingConference() {
        try {
            log.info("All speakers from conference retrieved!");
            return speakerService.getAllSpeakersByUpcomingConferenceId();
        } catch (Exception e) {
            log.warn("Failed to retrieve all speakers from conference!", e);
            throw new RuntimeException("Failed to retrieve all speakers from conference", e);
        }
    }

    @GetMapping("/checkIfUserIsSpeakerAndReturnSpeaker")
    public SpeakerDTO checkIfUserIsSpeakerAndReturnSpeaker(@RequestParam String userId) {
        try {
            log.info("Speaker retrieved!");
            return speakerService.checkIfUserIsSpeakerAndReturnSpeaker(UUID.fromString(userId));
        } catch (Exception e) {
            log.warn("Failed to retrieve speaker!", e);
            throw new RuntimeException("Failed to retrieve speaker", e);
        }
    }

    @GetMapping("/checkIfUserIsSpeaker")
    public boolean checkIfUserIsSpeaker(@RequestParam String userId) {
        try {
            log.info("Speaker retrieved!");
            return speakerService.checkIfUserIsSpeaker(UUID.fromString(userId));
        } catch (Exception e) {
            log.warn("Failed to retrieve speaker!", e);
            throw new RuntimeException("Failed to retrieve speaker", e);
        }
    }

    @GetMapping("/getSpeakerIdByUserId")
    public UUID getSpeakerIdByUserId(@RequestParam String userId) {
        try {
            log.info("Speaker id retrieved!");
            return speakerService.getSpeakerIdByUserId(UUID.fromString(userId));
        } catch (Exception e) {
            log.warn("Failed to retrieve speaker id!", e);
            throw new RuntimeException("Failed to retrieve speaker id", e);
        }
    }

    @GetMapping("/getUserIdsOfSpeakers")
    public List<UUID> getUserIdsOfSpeakers() {
        try {
            log.info("Getting user ids of speakers!");
            return speakerService.getUserIdsOfSpeakers();
        } catch (Exception e) {
            log.warn("Failed to retrieve user ids of speakers!", e);
            throw new RuntimeException("Failed to retrieve user ids of speakers", e);
        }
    }

    @Data
    public static class MakeSpeakerDto {
        private UUID userId;
        private String bio;
        private String phone;
    }

    @Data
    public static class UpdateSpeakerDto {
        private UUID speakerId;
        private String bio;
        private String phone;
    }
}

