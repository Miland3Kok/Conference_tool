package be.kdg.conference.model.event_interaction.questions;


import be.kdg.conference.controller.dto.questionDTOs.OpenQuestionDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private UUID talkId;

    @OneToMany(mappedBy = "question")
    private List<UserAnswer> userAnswers;

    private QuestionType questionType;

    public Question(UUID id, String content, UUID talkId) {
        this.id = id;
        this.content = content;
        this.talkId = talkId;
    }

    public Question() {
    }

    public Question(OpenQuestionDTO questionDTO) {
        this.id = questionDTO.getId();
        this.content = questionDTO.getContent();
        this.talkId = questionDTO.getTalkId();
    }
}
