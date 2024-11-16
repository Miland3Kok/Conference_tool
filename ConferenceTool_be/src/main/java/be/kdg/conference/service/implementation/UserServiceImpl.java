package be.kdg.conference.service.implementation;


import be.kdg.conference.controller.dto.MakeUserDTO;
import be.kdg.conference.controller.dto.ProfilePictureDTO;
import be.kdg.conference.controller.dto.UserDTO;
import be.kdg.conference.model.account.User;
import be.kdg.conference.repository.UserRepository;
import be.kdg.conference.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDTO userDto) {
        try {
            User user = convertUserDtoToUser(userDto);
            if (user.getUser_id() == null) {
                user.setUser_id(UUID.randomUUID());
            }
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("User could not be created!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new IllegalArgumentException("No users found.");
        }
    }


    @Override
    public User getUserById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchElementException("User not found!");
        }
    }

    @Override
    public UserDTO getUserDTObyId(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new UserDTO(userOptional.get());
        } else {
            throw new NoSuchElementException("User not found!");
        }
    }



    @Override
    public void updateUser(UUID userId, UserDTO userDto) {
        try {
            User existingUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            convertUserDtoToUser(userDto, existingUser);
            userRepository.save(existingUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("User could not be updated!");
        }
    }

    @Override
    public void deleteUser(UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("User could not get deleted");
        }
    }

    public User convertUserDtoToUser(UserDTO userDto) {
        User user = new User();
        user.setUser_id(userDto.getUser_id());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setOffice_function(userDto.getOffice_function());
        user.setRoles(userDto.getRoles());
        user.setProfilePicture(userDto.getProfilePicture());
        return user;
    }

    public void convertUserDtoToUser(UserDTO userDto, User user) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setOffice_function(userDto.getOffice_function());
        user.setRoles(userDto.getRoles());
        user.setProfilePicture(userDto.getProfilePicture());
    }

    @Override
    public void createUser(String userId, MakeUserDTO makeUserDTO) {
        User user = new User();
        user.setFirstName(makeUserDTO.getFirstname());
        user.setLastName(makeUserDTO.getLastname());
        user.setOffice_function(makeUserDTO.getFunction());
        user.setUser_id(UUID.fromString(userId));
        userRepository.save(user);
    }

    @Override
    public void uploadAvatar(UUID userId, MultipartFile avatar) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.setProfilePicture(avatar.getBytes());
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Avatar could not be uploaded", e);
        }
    }

    @Override
    public ProfilePictureDTO getProfilePictureOfUser(UUID userId) {
        ProfilePictureDTO profilePictureDTO = new ProfilePictureDTO();
        profilePictureDTO.setProfilePicture( userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")).getProfilePicture());
        return profilePictureDTO;
    }
}


