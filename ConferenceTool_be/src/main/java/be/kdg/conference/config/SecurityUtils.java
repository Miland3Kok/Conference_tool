package be.kdg.conference.config;

import be.kdg.conference.controller.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class SecurityUtils {
    public static UserDTO getAccountIdFromLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(UUID.fromString(jwt.getClaim("sub")));
        userDTO.setFirstName(jwt.getClaim("given_name"));
        userDTO.setLastName(jwt.getClaim("family_name"));
        return userDTO;
    }
}
