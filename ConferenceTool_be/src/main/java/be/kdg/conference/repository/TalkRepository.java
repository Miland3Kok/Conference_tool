package be.kdg.conference.repository;

import be.kdg.conference.controller.dto.TalkDTO;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TalkRepository extends JpaRepository<Talk, UUID> {

    @Query("SELECT t FROM talk t JOIN t.room r JOIN r.conference c WHERE c.conference_id = :conferenceId")
    List<Talk> findAllByConferenceId(@Param("conferenceId") UUID conferenceId);

}
