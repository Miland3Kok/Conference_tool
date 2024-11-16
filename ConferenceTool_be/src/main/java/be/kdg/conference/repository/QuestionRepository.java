package be.kdg.conference.repository;

import be.kdg.conference.controller.dto.questionDTOs.QuestionDTO;
import be.kdg.conference.model.event_interaction.questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    List<Question> findAllByTalkId(UUID id);
}
