package be.kdg.conference.model.event_interaction.questions;

import be.kdg.conference.controller.dto.questionDTOs.OptionDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.UUID;

@Data
@Entity
@Table(name="question_option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String optionText;
    private boolean isCorrect;

    @ManyToOne
    private MultipleChoiceQuestion multipleChoiceQuestion;

    public Option(UUID id, String optionText, boolean isCorrect, MultipleChoiceQuestion multipleChoiceQuestion) {
        this.id = id;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }

    public Option() {
    }

    public Option(OptionDTO optionDTO) {
        this.id = optionDTO.getId();
        this.optionText = optionDTO.getOptionText();
        this.isCorrect = optionDTO.isCorrect();
    }
}
