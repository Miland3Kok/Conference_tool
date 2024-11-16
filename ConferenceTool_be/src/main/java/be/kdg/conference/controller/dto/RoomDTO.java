package be.kdg.conference.controller.dto;

import be.kdg.conference.model.eventmanagement.Room;
import be.kdg.conference.model.eventmanagement.Talk;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class RoomDTO {

    private UUID room_id;
    private String name;
    private UUID conference_id;
    private List<TalkDTO> talks;

    public RoomDTO(UUID room_id, String name, UUID conference_id, List<Talk> talks) {
        this.room_id = room_id;
        this.name = name;
        this.conference_id = conference_id;
        this.talks = talks.stream().map(TalkDTO::new).collect(Collectors.toList());
    }

    public RoomDTO(Room room) {
        this.room_id = room.getRoom_id();
        this.name = room.getName();
        this.conference_id = room.getConference().getConference_id();
        this.talks = room.getTalks().stream().map(TalkDTO::new).collect(Collectors.toList());
    }
}
