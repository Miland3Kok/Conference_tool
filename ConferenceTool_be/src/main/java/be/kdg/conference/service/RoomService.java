package be.kdg.conference.service;

import be.kdg.conference.controller.dto.RoomDTO;
import be.kdg.conference.model.eventmanagement.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    RoomDTO createRoom(RoomDTO roomDTO);

    List<RoomDTO> getAllRooms();

    Room getRoomById(UUID id);

    RoomDTO getRoomDTOById(UUID id);

    RoomDTO updateRoom(RoomDTO roomDTO);

    void deleteRoom(UUID id);


    List<RoomDTO> getRoomsForConference(UUID id);

}
