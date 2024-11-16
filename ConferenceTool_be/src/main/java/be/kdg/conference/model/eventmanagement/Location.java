package be.kdg.conference.model.eventmanagement;

import be.kdg.conference.controller.dto.LocationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity(name = "location")
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID location_id;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(nullable = false, length = 10)
    private String number;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 50)
    private String postal_code;

    @Column(nullable = false, length = 50)
    private String country;

    private String extraInfo;

    public Location(LocationDTO locationDTO) {
        this.location_id = UUID.randomUUID();
        this.street = locationDTO.getStreet();
        this.number = locationDTO.getNumber();
        this.city = locationDTO.getCity();
        this.postal_code = locationDTO.getPostal_code();
        this.country = locationDTO.getCountry();
        this.extraInfo = locationDTO.getExtraInfo();
    }
}
