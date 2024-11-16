package be.kdg.conference.controller.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MakeTalkDTO {
    private List<UUID> speakers_id;
    private String title;
    private String description;
    private String start_date;
    private String end_date;
    private UUID room;
}
