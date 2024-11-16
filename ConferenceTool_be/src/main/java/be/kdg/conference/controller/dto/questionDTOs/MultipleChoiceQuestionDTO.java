package be.kdg.conference.controller.dto.questionDTOs;

import be.kdg.conference.model.event_interaction.questions.MultipleChoiceQuestion;
import be.kdg.conference.model.event_interaction.questions.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestionDTO {
    private UUID id;
    private String content;
    private UUID talkId;
    private List<OptionDTO> options;
    private String type;


    public MultipleChoiceQuestionDTO(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.talkId = question.getTalkId();
        this.options = ((MultipleChoiceQuestion) question).getOptionTexts().stream().map(OptionDTO::new).toList();
        this.type = "MULTIPLE_CHOICE";
    }
}
