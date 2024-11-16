package be.kdg.conference.service;

import be.kdg.conference.controller.dto.questionDTOs.UserAnswerDTO;

import java.util.List;
import java.util.UUID;

public interface UserAnswerService {


    List<UserAnswerDTO> getUserAnswersOfQuestion(UUID uuid);
}
