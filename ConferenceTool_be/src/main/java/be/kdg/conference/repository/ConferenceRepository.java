package be.kdg.conference.repository;


import be.kdg.conference.model.eventmanagement.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, UUID> {

    @Query("SELECT c FROM conference c WHERE c.active = true AND c.conference_id != :id")
    Optional<Conference> findByActiveTrueAndIdNot(UUID id);

    Optional<Conference> findByActiveTrue();

    boolean existsByName(String name);
}
