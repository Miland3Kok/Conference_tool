package be.kdg.conference.controller;

import be.kdg.conference.exception.DataAccessErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import be.kdg.conference.controller.dto.ConferenceDTO;
import be.kdg.conference.controller.dto.ConferenceFloorPlanDTO;
import be.kdg.conference.controller.dto.RoomDTO;
import be.kdg.conference.controller.dto.TalkDTO;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.service.ConferenceService;
import be.kdg.conference.service.RoomService;
import be.kdg.conference.service.TalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/conference")
public class ConferenceController {
    private final ConferenceService conferenceService;
    private final TalkService talkService;
    private final RoomService roomService;

    public ConferenceController(ConferenceService conferenceService, TalkService talkService, RoomService roomService) {
        this.conferenceService = conferenceService;
        this.talkService = talkService;
        this.roomService = roomService;
    }

    //TODO make create function return object
    @PostMapping("/create")
    public ResponseEntity<?> createConference(@RequestBody ConferenceDTO conferenceDTO) {
        log.info("Creating conference...");
        try {
            conferenceService.createConference(conferenceDTO);
            log.info("Conference created successfully");
            return ResponseEntity.ok().build();
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllConferences() {
        log.info("Retrieving all conferences...");
        try {
            List<ConferenceDTO> conferences = conferenceService.getAllConferences();
            if (conferences.isEmpty()) {
                log.info("No conferences found.");
                return ResponseEntity.noContent().build();
            }
            log.info("Conferences retrieved successfully.");
            return ResponseEntity.ok(conferences);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/first")
    public ResponseEntity<?> getLatestConference() {
        log.info("Retrieving latest conference...");
        try {
            ConferenceDTO conference = conferenceService.getLatestConference();
            if (conference == null) {
                log.info("No conferences found.");
                return ResponseEntity.noContent().build();
            }
            log.info("Latest conference retrieved successfully.");
            return ResponseEntity.ok(conference);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConferenceById(@PathVariable UUID id) {
        log.info("Retrieving conference with ID: " + id);
        try {
            ConferenceDTO conference = conferenceService.getConferenceDTOById(id);
            if (conference == null) {
                log.info("Conference not found with ID: " + id);
                return ResponseEntity.noContent().build();
            }
            log.info("Conference retrieved successfully.");
            return ResponseEntity.ok(conference);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteConference(@RequestParam UUID conferenceUUID) {
        log.info("Deleting conference with ID: " + conferenceUUID);
        try {
            conferenceService.deleteConference(conferenceUUID);
            log.info("Conference deleted successfully.");
            return ResponseEntity.ok().build();
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateConference(@RequestBody ConferenceDTO conferenceDTO) {
        log.info("Updating conference with ID: " + conferenceDTO.getConference_id());
        try {
            conferenceService.updateConference(conferenceDTO);
            log.info("Conference updated successfully");
            return ResponseEntity.ok().build();
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleConferenceStatus(@PathVariable UUID id) {
        log.info("Toggling conference status with ID: " + id);
        try {
            conferenceService.toggleConferenceStatus(id);
            log.info("Conference status toggled successfully.");
            return ResponseEntity.ok().build();
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-active")
    public ResponseEntity<?> getActiveConference() {
        log.info("Retrieving active conference...");
        try {
            ConferenceFloorPlanDTO conferences = conferenceService.getActiveConference();
            log.info("Active conference retrieved successfully.");
            return ResponseEntity.ok(conferences);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/talks")
    public ResponseEntity<?> getAllTalksForConference(@PathVariable UUID id) {
        log.info("Retrieving talks for conference with ID: " + id);
        try {
            List<TalkDTO> allTalks = talkService.getAllTalksForConference(id);
            log.info("Talks retrieved successfully.");
            return ResponseEntity.ok(allTalks);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/rooms")
    public ResponseEntity<?> getRoomsForConference(@PathVariable UUID id) {
        log.info("Retrieving rooms for conference with ID: " + id);
        try {
            List<RoomDTO> rooms = roomService.getRoomsForConference(id);
            log.info("Rooms retrieved successfully.");
            return ResponseEntity.ok(rooms);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload-floorplan")
    public ResponseEntity<?> uploadFloorplan(@RequestParam("file") MultipartFile file, @RequestParam("conferenceId") UUID conferenceId) {
        log.info("Uploading floorplan for conference with ID: " + conferenceId);
        try {
            conferenceService.saveFloorplanImage(file, conferenceId);
            log.info("Floorplan uploaded successfully.");
            return ResponseEntity.ok().build();
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/floorplan")
    public ResponseEntity<?> getFloorPlan(@PathVariable UUID id) {
        log.info("Retrieving floorplan for conference with ID: " + id);
        try {
            Conference conference = conferenceService.getConferenceById(id);
            byte[] floorPlanImage = conference.getFloorPlanImage();
            log.info("Floorplan retrieved successfully.");
            return ResponseEntity.ok().body(floorPlanImage);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error occurred");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
