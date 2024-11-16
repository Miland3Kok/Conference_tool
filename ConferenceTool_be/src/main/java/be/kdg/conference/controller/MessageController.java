package be.kdg.conference.controller;

import be.kdg.conference.config.SecurityUtils;
import be.kdg.conference.controller.dto.UserDTO;
import be.kdg.conference.service.MessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAnyAuthority('speaker')")
    @PostMapping("/createOrganisation")
    public ResponseEntity<?> createMessageForOrganisation(@RequestBody MakeMessageDTO makeMessageDTO) {
        try {
            UserDTO user = SecurityUtils.getAccountIdFromLoggedInUser();
            messageService.createMessageForOrganisation(makeMessageDTO, user.getUser_id());
            log.info("Message created for organisation!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to create message for organisation!", e);
            throw new RuntimeException("Failed to create message for organisation", e);
        }
    }

    @PreAuthorize("hasAnyAuthority('speaker')")
    @PostMapping("/createTalk")
    public ResponseEntity<?> createMessageForConference(@RequestBody MakeMessageDTO makeMessageDTO) {
        try {
            UserDTO user = SecurityUtils.getAccountIdFromLoggedInUser();
            messageService.createMessageForTalk(makeMessageDTO, user.getUser_id());
            log.info("Message created for conference!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to create message for talk!", e);
            throw new RuntimeException("Failed to create message for talk", e);
        }
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @GetMapping("/getMessagesForOrganisation")
    public ResponseEntity<?> getMessagesForOrganisation() {
        try {
            return ResponseEntity.ok(messageService.getMessagesForOrganisation());
        } catch (Exception e) {
            log.warn("Failed to get messages for organisation!", e);
            throw new RuntimeException("Failed to get messages for organisation", e);
        }
    }

    @GetMapping("/getMessagesForTalk")
    public ResponseEntity<?> getMessagesForTalk(@RequestParam UUID talkId) {
        try {
            return ResponseEntity.ok(messageService.getMessagesForTalk(talkId));
        } catch (Exception e) {
            log.warn("Failed to get messages for conference!", e);
            throw new RuntimeException("Failed to get messages for conference", e);
        }
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @GetMapping("/getMessageDetails")
    public ResponseEntity<?> getMessageDetails(@RequestParam UUID messageId) {
        try {
            return ResponseEntity.ok(messageService.getMessageDetails(messageId));
        } catch (Exception e) {
            log.warn("Failed to get message details!", e);
            throw new RuntimeException("Failed to get message details", e);
        }
    }

    @Data
    public static class MakeMessageDTO {
        private String subject;
        private String message;
        private boolean important;
    }
}
