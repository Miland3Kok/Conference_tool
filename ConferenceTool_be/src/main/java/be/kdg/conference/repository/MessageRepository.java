package be.kdg.conference.repository;

import be.kdg.conference.model.eventmanagement.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findAllByForOrganisationTrue();
    @Query("SELECT m FROM message m WHERE m.conference.conference_id = :conferenceId")
    List<Message> findAllByConference_id(UUID conferenceId);
}
