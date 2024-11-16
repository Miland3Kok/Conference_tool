package be.kdg.conference.controller.dto;

import be.kdg.conference.model.eventmanagement.Speaker;
import be.kdg.conference.model.eventmanagement.Talk;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class TalkDTO {
    private UUID talk_id;
    private List<TalkSpeakerDTO> speakers;
    private String title;
    private String description;
    private String start_date;
    private String end_date;
    private UUID room;

    public TalkDTO(Talk talk) {
        this.talk_id = talk.getTalk_id();
        this.speakers = new ArrayList<>();
        for (Speaker speaker : talk.getSpeakers()) {
            this.speakers.add(new TalkSpeakerDTO(speaker));
        }
        this.title = talk.getTitle();
        this.description = talk.getDescription();
        this.start_date = talk.getStart_date().toString();
        this.end_date = talk.getEnd_date().toString();
        this.room = talk.getRoom().getRoom_id();
    }
}
