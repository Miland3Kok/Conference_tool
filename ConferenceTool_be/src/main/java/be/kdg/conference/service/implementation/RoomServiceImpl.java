package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.RoomDTO;
import be.kdg.conference.exception.DataAccessErrorException;
import be.kdg.conference.exception.already_exists.RoomAlreadyExistsException;
import be.kdg.conference.exception.not_found.ConferenceNotFoundException;
import be.kdg.conference.exception.not_found.RoomNotFoundException;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Room;
import be.kdg.conference.repository.RoomRepository;
import be.kdg.conference.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ConferenceServiceImpl conferenceService;

    public RoomServiceImpl(RoomRepository roomRepository, ConferenceServiceImpl conferenceService) {
        this.roomRepository = roomRepository;
        this.conferenceService = conferenceService;
    }

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        try {
            Optional<Conference> conferenceOptional = Optional.ofNullable(conferenceService.getConferenceById(roomDTO.getConference_id()));
            if (conferenceOptional.isEmpty()) {
                throw new ConferenceNotFoundException(roomDTO.getConference_id());
            }
            if (roomRepository.existsByConferenceAndName(conferenceOptional.get(), roomDTO.getName())) {
                throw new RoomAlreadyExistsException(roomDTO.getName());
            }
            Room room = new Room(roomDTO, conferenceOptional.get());
            Room savedRoom = roomRepository.save(room);
            return new RoomDTO(savedRoom);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error creating room", e);
        }
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        try {
            return roomRepository.findAll().stream().map(RoomDTO::new).toList();
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error getting all rooms", e);
        }
    }

    @Override
    public Room getRoomById(UUID id) {
        try {
            return roomRepository.findById(id)
                    .orElseThrow(() -> new RoomNotFoundException(id));
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error getting room by ID", e);
        }
    }

    @Override
    public RoomDTO getRoomDTOById(UUID id) {
        try {
            return roomRepository.findById(id)
                    .map(RoomDTO::new)
                    .orElseThrow(() -> new RoomNotFoundException(id));
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error getting room DTO by ID", e);
        }
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        try {
            Room room = roomRepository.findById(roomDTO.getRoom_id())
                    .orElseThrow(() -> new RoomNotFoundException(roomDTO.getRoom_id()));
            room.setName(roomDTO.getName());
            Room savedRoom = roomRepository.save(room);
            return new RoomDTO(savedRoom);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error updating room", e);
        }
    }

    @Override
    public void deleteRoom(UUID id) {
        try {
            if (roomRepository.existsById(id)) {
                roomRepository.deleteById(id);
            } else {
                throw new RoomNotFoundException(id);
            }
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error deleting room", e);
        }
    }

    @Override
    public List<RoomDTO> getRoomsForConference(UUID id) {
        try {
            Optional<Conference> conference = Optional.ofNullable(conferenceService.getConferenceById(id));
            if (conference.isEmpty()) {
                throw new ConferenceNotFoundException(id);
            }
            return roomRepository.findAllByConference(conference.get()).stream().map(RoomDTO::new).toList();
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Error getting rooms for conference", e);
        }
    }
}
