package be.kdg.conference.controller.dto;

import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Room;
import be.kdg.conference.model.eventmanagement.Talk;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;


@Data
public class ConferenceDTO {
    private UUID conference_id;
    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private UUID location_id;
    private List<UUID> rooms;
    private boolean active;

    public ConferenceDTO(Conference conference) {
        this.conference_id = conference.getConference_id();
        this.name = conference.getName();
        this.description = conference.getDescription();
        this.start_date = conference.getStart_date().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end_date = conference.getEnd_date().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.location_id = conference.getLocation().getLocation_id();
        this.rooms = new ArrayList<>();
        for (Room room : conference.getRooms()) {
            this.rooms.add(room.getRoom_id());
        }
        this.active = conference.isActive();
    }

    public ConferenceDTO(String name, String description, String start_date, String end_date, UUID location_id, boolean active) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location_id = location_id;
        this.rooms = new ArrayList<>();
        this.active = active;
    }


}
