package be.kdg.conference.controller.dto;

import lombok.Data;

@Data
public class MakeUserDTO {
    private String firstname;
    private String lastname;
    private String function;
    private String username;
    private String email;
    private String role;
}
