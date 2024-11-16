package be.kdg.conference.model.event_interaction.questions;

import be.kdg.conference.controller.dto.questionDTOs.ClosedQuestionDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ClosedQuestion extends Question {
    private String correctAnswer;
    private String falseAnswer;

    public ClosedQuestion(ClosedQuestionDTO questionDTO) {
        super(questionDTO.getId(), questionDTO.getContent(), questionDTO.getTalkId());
        this.correctAnswer = questionDTO.getCorrectAnswer();
        this.falseAnswer = questionDTO.getFalseAnswer();
    }

    public ClosedQuestion() {

    }


}

