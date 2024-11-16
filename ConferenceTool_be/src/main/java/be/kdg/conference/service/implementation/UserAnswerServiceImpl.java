package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.questionDTOs.UserAnswerDTO;
import be.kdg.conference.model.event_interaction.questions.Question;
import be.kdg.conference.repository.UserAnswerRepository;
import be.kdg.conference.service.QuestionService;
import be.kdg.conference.service.UserAnswerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final QuestionService questionService;

    public UserAnswerServiceImpl(UserAnswerRepository userAnswerRepository, QuestionService questionService) {
        this.userAnswerRepository = userAnswerRepository;
        this.questionService = questionService;
    }

    @Override
    public List<UserAnswerDTO> getUserAnswersOfQuestion(UUID uuid) {
        Question question = questionService.getQuestionById(uuid);
        return userAnswerRepository.findAllByQuestion(question)
                .stream()
                .map(UserAnswerDTO::new)
                .collect(Collectors.toList());

    }
}
