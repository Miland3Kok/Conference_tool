package be.kdg.conference.controller.dto;

import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Room;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ConferenceFloorPlanDTO {
    private UUID conference_id;
    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private UUID location_id;
    private List<UUID> rooms;
    private boolean active;
    private byte[] floorPlanImage;



    public ConferenceFloorPlanDTO(Conference conference) {
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
        this.floorPlanImage = conference.getFloorPlanImage();
    }
}
