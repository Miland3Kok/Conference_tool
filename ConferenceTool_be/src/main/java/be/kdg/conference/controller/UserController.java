package be.kdg.conference.controller;


import be.kdg.conference.controller.dto.UserDTO;
import be.kdg.conference.model.account.User;
import be.kdg.conference.service.implementation.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        try {
            userService.createUser(userDto);
            log.info("User created!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllUsers() {
        try {
            log.info("Getting all users...");
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            log.warn("No users found!");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        try {
            log.info("Getting user");
            return ResponseEntity.ok(userService.getUserDTObyId(id));
        } catch (Exception e) {
            log.warn("No user found");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestParam UUID userId, @RequestBody UserDTO userDto) {
        try {
            userService.updateUser(userId, userDto);
            log.info("User updated!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam UUID userId) {
        try {
            userService.deleteUser(userId);
            log.info("User deleted!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam UUID userId, @RequestParam MultipartFile file) {
        try {
            userService.uploadAvatar(userId, file);
            log.info("Avatar uploaded!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getProfilePictureOfUser/{userId}")
    public ResponseEntity<?> getProfilePictureOfUser(@PathVariable UUID userId) {
        try {
            log.info("Getting profile picture of user");
            User user = userService.getUserById(userId);
            if (user.getProfilePicture() == null) {
                return ResponseEntity.badRequest().build();
            }
            byte[] profilePicture = user.getProfilePicture();
            return ResponseEntity.ok().body(profilePicture);
        } catch (Exception e) {
            log.warn("No profile picture found");
            return ResponseEntity.badRequest().build();
        }
    }
}
