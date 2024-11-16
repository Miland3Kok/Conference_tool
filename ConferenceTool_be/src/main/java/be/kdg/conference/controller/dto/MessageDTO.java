package be.kdg.conference.controller.dto;

import be.kdg.conference.model.eventmanagement.Message;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageDTO {
    private UUID message_id;
    private String subject;
    private String message;
    private boolean important;
    private SpeakerDTO speaker;
    private ConferenceDTO conference;
    private boolean forOrganisation;

    public MessageDTO(Message message) {
        this.message_id = message.getMessage_id();
        this.subject = message.getSubject();
        this.message = message.getMessage();
        this.important = message.isImportant();
        this.speaker = new SpeakerDTO(message.getSpeaker());
        this.conference = new ConferenceDTO(message.getConference());
        this.forOrganisation = message.isForOrganisation();
    }
}
