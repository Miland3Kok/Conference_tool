package be.kdg.conference.repository;

import be.kdg.conference.model.event_interaction.questions.Question;
import be.kdg.conference.model.event_interaction.questions.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, UUID> {
   List<UserAnswer> findAllByQuestion(Question question);
}
