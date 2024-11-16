package be.kdg.conference.model.event_interaction.questions;

import be.kdg.conference.controller.dto.questionDTOs.OpenQuestionDTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class OpenQuestion extends Question{
    public OpenQuestion(OpenQuestionDTO questionDTO) {
        super(questionDTO);
    }
}
