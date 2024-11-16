package be.kdg.conference.repository;

import be.kdg.conference.model.event_interaction.questions.MultipleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, UUID> {
    Iterable<MultipleChoiceQuestion> findAllByTalkId(UUID id);
}
