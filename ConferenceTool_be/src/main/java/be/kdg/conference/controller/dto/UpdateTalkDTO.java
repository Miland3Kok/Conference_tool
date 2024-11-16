package be.kdg.conference.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateTalkDTO {
    private UUID talk_id;
    private String title;
    private String description;
    private String start_date;
    private String end_date;
    private UUID room;
}
