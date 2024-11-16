package be.kdg.conference.service;

import be.kdg.conference.controller.MessageController.*;
import be.kdg.conference.controller.dto.MessageDTO;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    void createMessageForOrganisation(MakeMessageDTO makeMessageDTO, UUID userId);

    List<MessageDTO> getMessagesForOrganisation();

    void createMessageForTalk(MakeMessageDTO makeMessageDTO, UUID userId);

    List<MessageDTO> getMessagesForTalk(UUID talkId);

    MessageDTO getMessageDetails(UUID messageId);
}
