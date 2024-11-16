package be.kdg.conference.controller.dto.questionDTOs;

import be.kdg.conference.model.event_interaction.questions.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenQuestionDTO {
    private UUID id;
    private String content;
    private UUID talkId;
    private String type;

    public OpenQuestionDTO(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.talkId = question.getTalkId();
        this.type = "OPEN";
    }
}
