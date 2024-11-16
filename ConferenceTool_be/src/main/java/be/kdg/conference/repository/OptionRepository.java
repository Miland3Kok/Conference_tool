package be.kdg.conference.repository;

import be.kdg.conference.model.event_interaction.questions.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface OptionRepository extends JpaRepository<Option, UUID> {
    Collection<Option> findAllByMultipleChoiceQuestionId(UUID id);
}
