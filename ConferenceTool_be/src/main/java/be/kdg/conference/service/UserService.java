package be.kdg.conference.service;


import be.kdg.conference.controller.KeycloakController;
import be.kdg.conference.controller.dto.MakeUserDTO;
import be.kdg.conference.controller.dto.ProfilePictureDTO;
import be.kdg.conference.controller.dto.UserDTO;
import be.kdg.conference.model.account.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {


    void createUser(UserDTO userDto);

    List<User> getAllUsers();

    void updateUser(UUID userId, UserDTO userDto);

    void deleteUser(UUID userId);

    User convertUserDtoToUser(UserDTO userDto);

    void convertUserDtoToUser(UserDTO userDto, User user);

    User getUserById(UUID userId);

    UserDTO getUserDTObyId(UUID user);

    void createUser(String userId, MakeUserDTO makeUserDTO);

    void uploadAvatar(UUID userId, MultipartFile avatar);

    ProfilePictureDTO getProfilePictureOfUser(UUID userId);
}
