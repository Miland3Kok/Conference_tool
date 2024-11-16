package be.kdg.conference.controller.dto;

import lombok.Data;

@Data
public class ProfilePictureDTO {
    private byte[] profilePicture;

    public ProfilePictureDTO() {
    }

    public ProfilePictureDTO(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
