package be.kdg.conference.controller.dto.questionDTOs;



import be.kdg.conference.model.event_interaction.questions.Option;
import lombok.Data;

import java.util.UUID;

@Data
public class OptionDTO {
    private UUID id;
    private String optionText;
    private boolean isCorrect;
    private UUID questionId;

    public OptionDTO(Option option) {
        this.id = option.getId();
        this.optionText = option.getOptionText();
        this.isCorrect = option.isCorrect();
    }

    public OptionDTO(UUID id, String optionText, boolean isCorrect, UUID questionId) {
        this.id = id;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }
}
