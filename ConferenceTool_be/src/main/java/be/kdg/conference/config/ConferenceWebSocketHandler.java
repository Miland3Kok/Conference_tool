package be.kdg.conference.config;

import be.kdg.conference.controller.dto.ConferenceDTO;
import be.kdg.conference.service.implementation.ConferenceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class ConferenceWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ConferenceServiceImpl conferenceService;

    @Autowired
    public ConferenceWebSocketHandler(ConferenceServiceImpl conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("New ws connection established");
        sendConferencesToSession(session);
        sessions.add(session);
    }

    private void sendConferencesToSession(WebSocketSession session) {
        try {
            List<ConferenceDTO> conferences = conferenceService.getAllConferences();
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(conferences));
            session.sendMessage(message);
        } catch (Exception e) {
            log.error("Error sending conferences to session", e);
        }
    }
}
