package be.kdg.conference.controller.dto;

import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

@Data
public class KeycloakUserDTO {
    private String id;
    private String firstname;
    private String lastname;
    private String function;
    private String username;
    private String email;

    public KeycloakUserDTO(UserRepresentation user) {
        this.id = user.getId();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        //if (user.getAttributes().containsKey("function"))
            //this.function = user.getAttributes().get("function").get(0);
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
