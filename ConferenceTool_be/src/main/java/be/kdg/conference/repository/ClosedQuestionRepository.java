package be.kdg.conference.repository;

import be.kdg.conference.model.event_interaction.questions.ClosedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClosedQuestionRepository extends JpaRepository<ClosedQuestion, UUID> {

    Iterable<ClosedQuestion> findAllByTalkId(UUID id);
}
