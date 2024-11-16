package be.kdg.conference.controller.dto.questionDTOs;

import be.kdg.conference.model.event_interaction.questions.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class QuestionDTO {

    private UUID id;
    private String content;
    private UUID talkId;
    private String correctAnswer;
    private String falseAnswer;
    private List<Option> options;
    private String type;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.talkId = question.getTalkId();


        if (question instanceof ClosedQuestion) {
            this.type = "Closed";
        } else if (question instanceof MultipleChoiceQuestion) {
            this.type = "MultipleChoice";
        } else if (question instanceof OpenQuestion) {
            this.type = "Open";
        }
    }

    public QuestionDTO(ClosedQuestion question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.talkId = question.getTalkId();
        this.correctAnswer = question.getCorrectAnswer();
        this.falseAnswer = question.getFalseAnswer();
        this.type = "Closed";
    }

    public QuestionDTO(MultipleChoiceQuestion question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.talkId = question.getTalkId();
        this.type = "MultipleChoice";
    }

    public QuestionDTO(OpenQuestion question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.talkId = question.getTalkId();
        this.type = "Open";
    }
}
