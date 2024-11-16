package be.kdg.conference.controller.dto;

import be.kdg.conference.model.eventmanagement.Speaker;
import jakarta.persistence.Lob;
import lombok.Data;

import java.util.UUID;

@Data
public class SpeakerDTO {
    private UUID speaker_id;
    private UserDTO user;
    private String firstName;
    private String lastName;
    private String office_function;
    private String bio;
    private String phone;
    @Lob
    private byte[] profilePicture;

    public SpeakerDTO(Speaker speaker) {
        this.speaker_id = speaker.getSpeaker_id();
        this.user = new UserDTO(speaker.getUser());
        this.firstName = speaker.getUser().getFirstName();
        this.lastName = speaker.getUser().getLastName();
        this.office_function = speaker.getUser().getOffice_function();
        this.bio = speaker.getBio();
        this.phone = speaker.getPhone();
        this.profilePicture = speaker.getUser().getProfilePicture();
    }
}
