package be.kdg.conference.controller.dto;

import be.kdg.conference.model.account.Role;
import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.Message;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID user_id;

    @NotBlank(message= "First name is required!")
    private String firstName;

    @NotBlank(message= "Last name is required!")
    private String lastName;

    @NotBlank(message= "Office function is required!")
    private String office_function;
    private List<Role> roles;
    private byte[] profilePicture;


    public UserDTO() {
    }

    public UserDTO(User user) {
        this.user_id = user.getUser_id();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.office_function = user.getOffice_function();
        this.roles = user.getRoles();
        this.profilePicture = user.getProfilePicture();
    }
}
