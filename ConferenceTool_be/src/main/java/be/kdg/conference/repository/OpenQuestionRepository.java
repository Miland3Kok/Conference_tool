package be.kdg.conference.repository;

import be.kdg.conference.model.event_interaction.questions.OpenQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OpenQuestionRepository extends JpaRepository<OpenQuestion, UUID> {
    Iterable<OpenQuestion> findAllByTalkId(UUID id);
}
