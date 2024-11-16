package be.kdg.conference.repository;

import be.kdg.conference.model.eventmanagement.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    boolean existsByCityAndStreetAndNumber(String city, String street, String number);
}
