package be.kdg.conference.service;

import be.kdg.conference.controller.dto.KeycloakUserDTO;
import be.kdg.conference.controller.dto.MakeUserDTO;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface KeycloakService {
    void createUser(MakeUserDTO makeUserDTO) throws MessagingException;

    List<KeycloakUserDTO> getUsersInRole(String role);

    Map<Integer, String> getRoles();

    void uploadFile(MultipartFile file) throws IOException;

    void deleteUser(String userId);

    void addRoleToUser(String userId, String role);
}
