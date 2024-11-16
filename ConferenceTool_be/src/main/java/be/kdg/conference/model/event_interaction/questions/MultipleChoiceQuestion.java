package be.kdg.conference.model.event_interaction.questions;

import be.kdg.conference.controller.dto.questionDTOs.MultipleChoiceQuestionDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MultipleChoiceQuestion extends Question {

    @OneToMany(mappedBy = "multipleChoiceQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> optionTexts;

    public MultipleChoiceQuestion(MultipleChoiceQuestionDTO questionDTO) {
        super(questionDTO.getId(), questionDTO.getContent(), questionDTO.getTalkId());
        this.optionTexts = questionDTO.getOptions().stream().map(Option::new).toList();
    }

    public MultipleChoiceQuestion(UUID id, String content, UUID talkId, List<Option> optionTexts) {
        super(id, content, talkId);
        this.optionTexts = optionTexts;
    }


}
