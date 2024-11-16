package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.FeedbackDTO;
import be.kdg.conference.exception.DataAccessErrorException;
import be.kdg.conference.exception.FeedbackException;
import be.kdg.conference.exception.not_found.TalkNotFoundException;
import be.kdg.conference.model.account.User;
import be.kdg.conference.model.event_interaction.Feedback;
import be.kdg.conference.model.eventmanagement.Talk;
import be.kdg.conference.repository.FeedbackRepository;
import be.kdg.conference.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserServiceImpl userService;
    private final TalkServiceImpl talkService;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserServiceImpl userService, TalkServiceImpl talkService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
        this.talkService = talkService;
    }

    @Override
    public void createFeedback(FeedbackDTO feedbackDTO) {
        try {
            Feedback feedback = convertFeedbackDTOToFeedback(feedbackDTO);
            feedbackRepository.save(feedback);
        } catch (DataAccessException e) {
            throw new FeedbackException("Database error creating feedback");
        } catch (TalkNotFoundException e) {
            throw new FeedbackException("Talk not found while creating feedback");
        } catch (Exception e) {
            throw new FeedbackException("Error creating feedback");
        }
    }

    private Feedback convertFeedbackDTOToFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComment());

        User user = userService.getUserById(UUID.fromString(feedbackDTO.getUserId()));
        feedback.setUser(user);

        Talk talk = talkService.getTalkById(feedbackDTO.getTalkId());
        feedback.setTalk(talk);

        return feedback;
    }

    @Override
    public int getRatingOfTalk(UUID talkId) {
        try {
            return feedbackRepository.getRatingOfTalk(talkId);
        } catch (DataAccessException e) {
            throw new DataAccessErrorException("Data access exception getting rating of talk.", e);
        } catch (Exception e) {
            throw new FeedbackException("Error getting rating of talk");
        }
    }

    @Override
    public Boolean userHasGivenFeedback(UUID talkId, UUID userId) {
        try {
            return feedbackRepository.userHasGivenFeedback(talkId, userId);
        } catch (DataAccessException e) {
            throw new FeedbackException("Database error checking if user has given feedback");
        } catch (Exception e) {
            throw new FeedbackException("Error checking if user has given feedback");
        }
    }
}
