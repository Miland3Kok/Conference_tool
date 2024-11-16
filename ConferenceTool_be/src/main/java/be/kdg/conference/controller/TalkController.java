package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.AddSpeakerToTalkDTO;
import be.kdg.conference.controller.dto.MakeTalkDTO;
import be.kdg.conference.controller.dto.TalkDTO;
import be.kdg.conference.controller.dto.UpdateTalkDTO;
import be.kdg.conference.service.implementation.TalkServiceImpl;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/talk")
public class TalkController {

    private final TalkServiceImpl talkService;

    public TalkController(TalkServiceImpl talkService) {
        this.talkService = talkService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTalk(@RequestBody @NotNull MakeTalkDTO makeTalkDTO) {
        log.info("Creating talk...");
        log.info("makeTalkDTO: {}", makeTalkDTO);
        try {
            talkService.createTalk(makeTalkDTO);
            log.info("Talk created!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to create talk!", e);
            throw new RuntimeException("Failed to create talk", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTalk(@PathVariable UUID id) {
        try {
            TalkDTO talk = talkService.getTalk(id);
            return ResponseEntity.ok(talk);
        } catch (Exception e) {
            log.warn("Failed to retrieve talk with ID: {}", id, e);
            throw new RuntimeException("Failed to retrieve talk with ID: " + id, e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTalk(@RequestBody @NotNull UpdateTalkDTO updateTalkDTO) {
        try {
            talkService.updateTalk(updateTalkDTO);
            log.info("Talk updated!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to update talk!", e);
            throw new RuntimeException("Failed to update talk", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTalk(@PathVariable UUID id) {
        try {
            talkService.deleteTalk(id);
            log.info("Talk deleted!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to delete talk!", e);
            throw new RuntimeException("Failed to delete talk", e);
        }
    }

    @PostMapping("/addSpeaker")
    public ResponseEntity<?> addSpeakerToTalk(@RequestBody AddSpeakerToTalkDTO addSpeakerToTalkDTO) {
        log.info("Adding speaker to talk...");
        try {
            talkService.addSpeakerToTalk(addSpeakerToTalkDTO);
            log.info("Speaker added to talk!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to add speaker to talk!", e);
            throw new RuntimeException("Failed to add speaker to talk", e);
        }
    }

    @DeleteMapping("/removeSpeaker")
    public ResponseEntity<?> removeSpeakerFromTalk(@RequestBody AddSpeakerToTalkDTO addSpeakerToTalkDTO) {
        log.info("Removing speaker from talk...");
        try {
            talkService.removeSpeakerFromTalk(addSpeakerToTalkDTO);
            log.info("Speaker removed from talk!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to remove speaker from talk!", e);
            throw new RuntimeException("Failed to remove speaker from talk", e);
        }
    }

    @PutMapping("/updateConference")
    public ResponseEntity<?> updateConference(@RequestParam String talk_id, @RequestParam String conference_id) {
        try {
            talkService.updateConference(UUID.fromString(talk_id), UUID.fromString(conference_id));
            log.info("Conference updated!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to update conference!", e);
            throw new RuntimeException("Failed to update conference", e);
        }
    }

    @PostMapping("/favourite")
    public ResponseEntity<?> addFavouriteTalk(@RequestParam @NotNull String userId, @RequestParam @NotNull String talkId) {
        try {
            talkService.addFavouriteTalk(UUID.fromString(userId), UUID.fromString(talkId));
            log.info("Talk added to favourites!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to add talk to favourites!", e);
            throw new RuntimeException("Failed to add talk to favourites", e);
        }
    }

    @DeleteMapping("/removeFavourite")
    public ResponseEntity<?> removeFavouriteTalk(@RequestParam @NotNull String userId, @RequestParam @NotNull String talkId) {
        try {
            talkService.removeFavouriteTalk(UUID.fromString(userId), UUID.fromString(talkId));
            log.info("Talk removed from favourites!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to remove talk from favourites!", e);
            throw new RuntimeException("Failed to remove talk from favourites", e);
        }
    }

    @GetMapping("/favourite/{userId}")
    public ResponseEntity<?> getFavouriteTalksByUserId(@PathVariable UUID userId) {
        try {
            List<TalkDTO> favouriteTalks = talkService.getFavouriteTalksByUserId(userId);
            return ResponseEntity.ok(favouriteTalks);
        } catch (Exception e) {
            log.warn("Failed to retrieve favourite talks for user with ID: {}", userId, e);
            throw new RuntimeException("Failed to retrieve favourite talks for user with ID: " + userId, e);
        }
    }


    @GetMapping("/favourite/{userId}/{conferenceId}")
    public ResponseEntity<?> getFavouriteTalksByUserIdAndConferenceId(@PathVariable UUID userId, @PathVariable UUID conferenceId) {
        try {
            List<TalkDTO> favouriteTalks = talkService.getFavouriteTalksByUserIdAndConferenceId(userId, conferenceId);
            return ResponseEntity.ok(favouriteTalks);
        } catch (Exception e) {
            log.warn("Failed to retrieve favourite talks for user with ID: {}", userId, e);
            throw new RuntimeException("Failed to retrieve favourite talks for user with ID: " + userId, e);
        }
    }
    @GetMapping("/getAmountOfFavorites/{talkId}")
    public ResponseEntity<?> getAmountOfFavorites(@PathVariable UUID talkId) {
        try {
            int amountOfFavorites = talkService.getAmountOfFavorites(talkId);
            return ResponseEntity.ok(amountOfFavorites);
        } catch (Exception e) {
            log.warn("Failed to retrieve amount of favourites for talk with ID: {}", talkId, e);
            throw new RuntimeException("Failed to retrieve amount of favourites for talk with ID: " + talkId, e);
        }
    }

    @GetMapping("/getTalksBySpeakerId/{speakerId}")
    public ResponseEntity<?> getTalksBySpeakerId(@PathVariable UUID speakerId) {
        try {
            List<TalkDTO> talks = talkService.getTalksBySpeakerId(speakerId);
            return ResponseEntity.ok(talks);
        } catch (Exception e) {
            log.warn("Failed to retrieve talks for speaker with ID: {}", speakerId, e);
            throw new RuntimeException("Failed to retrieve talks for speaker with ID: " + speakerId, e);
        }
    }

    @GetMapping("/getFeaturedTalksOfConference/{conferenceId}")
    public ResponseEntity<?> getFeaturedTalks(@PathVariable UUID conferenceId) {
        try {
            List<TalkDTO> talks = talkService.getFeaturedTalks(conferenceId);
            return ResponseEntity.ok(talks);
        } catch (Exception e) {
            log.warn("Failed to retrieve featured talks", e);
            throw new RuntimeException("Failed to retrieve featured talks", e);
        }
    }
}
