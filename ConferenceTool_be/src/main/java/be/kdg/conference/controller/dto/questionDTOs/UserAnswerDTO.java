package be.kdg.conference.controller.dto.questionDTOs;

import be.kdg.conference.model.event_interaction.questions.UserAnswer;
import lombok.Data;

import java.util.UUID;

@Data
public class UserAnswerDTO {
    private UUID userAnswerId;
    private UUID userId;
    private UUID questionId;
    private String answer;

    public UserAnswerDTO(UUID userAnswerId, UUID userId, UUID questionId, String answer) {
        this.userAnswerId = userAnswerId;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
    }

    public UserAnswerDTO(UserAnswer userAnswer) {
        this.userAnswerId = userAnswer.getId();
        this.userId = userAnswer.getUser().getUser_id();
        this.questionId = userAnswer.getQuestion().getId();
        this.answer = userAnswer.getAnswer();
    }
}
