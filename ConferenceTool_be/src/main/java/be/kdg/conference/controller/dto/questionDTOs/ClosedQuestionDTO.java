package be.kdg.conference.controller.dto.questionDTOs;

import be.kdg.conference.model.event_interaction.questions.ClosedQuestion;
import be.kdg.conference.model.event_interaction.questions.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosedQuestionDTO {
    private UUID id;
    private String content;
    private UUID talkId;
    private String correctAnswer;
    private String falseAnswer;
    private String type;

    public ClosedQuestionDTO(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.talkId = question.getTalkId();
        this.correctAnswer = ((ClosedQuestion) question).getCorrectAnswer();
        this.falseAnswer = ((ClosedQuestion) question).getFalseAnswer();
        this.type = "CLOSED";
    }

}
