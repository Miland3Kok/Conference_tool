package be.kdg.conference.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AddSpeakerToTalkDTO {
    private UUID talkId;
    private UUID speakerId;
}
