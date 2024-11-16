package be.kdg.conference.repository;

import be.kdg.conference.model.event_interaction.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
   @Query("SELECT ROUND(AVG(f.rating), 0) FROM feedback f WHERE f.talk.talk_id = (SELECT t.talk_id FROM talk t WHERE t.talk_id = (:talkId))")
    int getRatingOfTalk(UUID talkId);

   @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM feedback f WHERE f.talk.talk_id = (:talkId) AND f.user.user_id = (:userId)")
    Boolean userHasGivenFeedback(UUID talkId, UUID userId);
}
