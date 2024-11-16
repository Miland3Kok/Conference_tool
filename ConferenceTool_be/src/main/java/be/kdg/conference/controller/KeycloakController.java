package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.MakeUserDTO;
import be.kdg.conference.service.UserService;
import be.kdg.conference.service.implementation.KeycloakServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/keycloak")
public class KeycloakController {

    private final KeycloakServiceImpl keycloakService;

    public KeycloakController(KeycloakServiceImpl keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody MakeUserDTO makeUserDTO) {
        try {
            keycloakService.createUser(makeUserDTO);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            log.warn("Could not create user: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @GetMapping("/get/{role}")
    public ResponseEntity<?> getUsersInRole(@PathVariable String role) {
        try {
            log.info("access users in: " + role);
            return ResponseEntity.ok(keycloakService.getUsersInRole(role));
        } catch (Exception exception) {
            log.warn("Could not find users: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @PostMapping("/addRole/{userId}/{role}")
    public ResponseEntity<?> addRoleToUser(@PathVariable String userId, @PathVariable String role) {
        try {
            log.info("Adding role: " + role + " to user: " + userId);
            keycloakService.addRoleToUser(userId, role);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            log.warn("Could not add role: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            log.info("Deleting user: " + userId);
            keycloakService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            log.warn("Could not delete user: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles() {
        try {
            log.info("Getting roles.");
            return ResponseEntity.ok(keycloakService.getRoles());
        } catch (Exception exception) {
            log.warn("Could not find roles: " + exception);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('organizer', 'admin')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Uploading file");
            keycloakService.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            log.warn("Could not make users from file: " + exception);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }
}
