package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.RoomDTO;
import be.kdg.conference.exception.not_found.RoomNotFoundException;
import be.kdg.conference.service.implementation.RoomServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomServiceImpl roomService;

    public RoomController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        log.info("Creating room...");
        try {
            log.info("roomDTO: {}", roomDTO);
            RoomDTO createdRoom = roomService.createRoom(roomDTO);
            log.info("Room created successfully");
            return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating room: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        log.info("Retrieving all rooms...");
        try {
            List<RoomDTO> rooms = roomService.getAllRooms();
            log.info("Rooms retrieved successfully");
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable UUID id) {
        log.info("Retrieving room with ID: {}", id);
        try {
            RoomDTO room = roomService.getRoomDTOById(id);
            log.info("Room retrieved successfully");
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (RoomNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<RoomDTO> updateRoom(@RequestBody RoomDTO roomDTO) {
        log.info("Updating room...");
        try {
            RoomDTO updatedRoom = roomService.updateRoom(roomDTO);
            log.info("Room successfully updated!");
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (RoomNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable UUID id) {
        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RoomNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("GetRoomsByConferenceId/{id}")
    public ResponseEntity<List<RoomDTO>> getRoomsByConferenceId(@PathVariable UUID id) {
        List<RoomDTO> rooms = roomService.getRoomsForConference(id);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}
