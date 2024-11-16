package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.FeedbackDTO;
import be.kdg.conference.exception.DataAccessErrorException;
import be.kdg.conference.exception.FeedbackException;
import be.kdg.conference.service.implementation.FeedbackServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackServiceImpl feedbackService;

    public FeedbackController(FeedbackServiceImpl feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user', 'speaker', 'organizer')")
    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        log.info("Creating feedback...");
        try {
            feedbackService.createFeedback(feedbackDTO);
            log.info("Feedback created successfully");
            return ResponseEntity.ok().build();
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error creating feedback");
        } catch (FeedbackException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Feedback error creating feedback");
        } catch (Exception e) {
            log.error("Error creating feedback: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getRatingOfTalk")
    public ResponseEntity<?> getRatingOfTalk(@RequestParam UUID talkId) {
        try {
            int rating = feedbackService.getRatingOfTalk(talkId);
            return ResponseEntity.ok(rating);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error getting rating of talk");
        } catch (FeedbackException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Feedback error getting rating of talk");
        } catch (Exception e) {
            log.error("Error getting rating of talk: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("userHasGivenFeedback")
    public ResponseEntity<?> userHasGivenFeedback(@RequestParam UUID talkId, @RequestParam UUID userId) {
        try {
            boolean hasGivenFeedback = feedbackService.userHasGivenFeedback(talkId, userId);
            return ResponseEntity.ok(hasGivenFeedback);
        } catch (DataAccessErrorException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error checking if user has given feedback");
        } catch (FeedbackException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Feedback error checking if user has given feedback");
        } catch (Exception e) {
            log.error("Error checking if user has given feedback: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
