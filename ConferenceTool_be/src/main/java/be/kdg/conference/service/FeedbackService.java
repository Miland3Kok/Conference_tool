package be.kdg.conference.service;

import be.kdg.conference.controller.dto.FeedbackDTO;

import java.util.UUID;

public interface FeedbackService {
    void createFeedback(FeedbackDTO feedbackDTO);

    int getRatingOfTalk(UUID talkId);

    Boolean userHasGivenFeedback(UUID talkId, UUID userId);
}
