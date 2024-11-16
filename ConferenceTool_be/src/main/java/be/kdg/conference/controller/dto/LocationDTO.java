package be.kdg.conference.controller.dto;

import be.kdg.conference.model.eventmanagement.Location;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class LocationDTO implements Serializable {
    private UUID location_id;
    private String street;
    private String number;
    private String city;
    private String postal_code;
    private String country;
    private String extraInfo;

    public LocationDTO(Location location) {
        this.location_id = location.getLocation_id();
        this.street = location.getStreet();
        this.number = location.getNumber();
        this.city = location.getCity();
        this.postal_code = location.getPostal_code();
        this.country = location.getCountry();
        this.extraInfo = location.getExtraInfo();
    }

    public LocationDTO() {
    }
}
