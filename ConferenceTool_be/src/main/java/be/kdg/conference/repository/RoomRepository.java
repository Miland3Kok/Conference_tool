package be.kdg.conference.repository;


import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {

    Collection<Room> findAllByConference(Conference conference);

    boolean existsByConferenceAndName(Conference conference, String name);
}
