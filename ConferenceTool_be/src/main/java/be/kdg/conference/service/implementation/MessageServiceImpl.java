package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.MessageController.MakeMessageDTO;
import be.kdg.conference.controller.dto.MessageDTO;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Message;
import be.kdg.conference.model.eventmanagement.Speaker;
import be.kdg.conference.repository.ConferenceRepository;
import be.kdg.conference.repository.MessageRepository;
import be.kdg.conference.repository.SpeakerRepository;
import be.kdg.conference.repository.UserRepository;
import be.kdg.conference.service.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SpeakerRepository speakerRepository;
    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository,
                              SpeakerRepository speakerRepository,
                              ConferenceRepository conferenceRepository,
                              UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.speakerRepository = speakerRepository;
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createMessageForOrganisation(MakeMessageDTO makeMessageDTO, UUID userId) {
        createMessage(makeMessageDTO, userId, true);
    }

    @Override
    @Transactional
    public List<MessageDTO> getMessagesForOrganisation() {
        return messageRepository.findAllByForOrganisationTrue().stream()
                .filter(m -> m.getConference().isActive())
                .map(MessageDTO::new)
                .toList();
    }

    @Override
    @Transactional
    public void createMessageForTalk(MakeMessageDTO makeMessageDTO, UUID userId) {
        createMessage(makeMessageDTO, userId, false);
    }

    @Override
    public List<MessageDTO> getMessagesForTalk(UUID conferenceId) {
        return messageRepository.findAllByConference_id(conferenceId).stream()
                .filter(m -> m.getConference().isActive())
                .map(MessageDTO::new)
                .toList();
    }

    @Override
    public MessageDTO getMessageDetails(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(MessageDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Message not found!"));
    }

    private void createMessage(MakeMessageDTO makeMessageDTO, UUID userId, boolean forOrganisation) {
        Speaker speaker = userRepository.findById(userId)
                .map(user -> speakerRepository.findByUser(user)
                        .orElseThrow(() -> new IllegalArgumentException("Speaker not found!")))
                .orElseThrow();

        Conference conference = conferenceRepository.findAll().stream()
                .filter(Conference::isActive)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active conference found!"));

        Message message = new Message();
        message.setMessage(makeMessageDTO.getMessage());
        message.setSpeaker(speaker);
        message.setConference(conference);
        message.setForOrganisation(forOrganisation);
        message.setImportant(makeMessageDTO.isImportant());
        message.setSubject(makeMessageDTO.getSubject());

        messageRepository.save(message);
    }
}
