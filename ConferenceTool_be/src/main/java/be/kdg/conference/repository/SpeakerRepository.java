package be.kdg.conference.repository;

import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpeakerRepository extends JpaRepository<Speaker, UUID> {


    @Query("SELECT ts from talk t JOIN t.speakers ts WHERE t.room.conference.conference_id = :conferenceId")
    List<Speaker> findSpeakersByConferenceId(@Param("conferenceId") UUID conferenceId);

    Optional<Speaker> findByUser(User user);
}
