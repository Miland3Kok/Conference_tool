package be.kdg.conference.controller.service;

import be.kdg.conference.controller.SpeakerController;
import be.kdg.conference.controller.SpeakerController.MakeSpeakerDto;
import be.kdg.conference.controller.SpeakerController.UpdateSpeakerDto;
import be.kdg.conference.controller.dto.SpeakerDTO;
import be.kdg.conference.controller.dto.UserDTO;
import be.kdg.conference.model.account.Role;
import be.kdg.conference.model.account.Roles;
import be.kdg.conference.model.account.User;
import be.kdg.conference.repository.RoleRepository;
import be.kdg.conference.repository.SpeakerRepository;
import be.kdg.conference.repository.UserRepository;
import be.kdg.conference.service.SpeakerService;
import be.kdg.conference.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpeakerServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SpeakerService speakerService;

    @Autowired
    SpeakerRepository speakerRepository;

    @Autowired
    RoleRepository roleRepository;

    private User myUser;

    @AfterEach
    public void after() {
        speakerRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testIfICanMakeASpeaker() {
        makeUser();
        MakeSpeakerDto speaker = new MakeSpeakerDto();
        speaker.setUserId(myUser.getUser_id());
        speaker.setBio("I am a speaker");
        speaker.setPhone("123456789");

        speakerService.createSpeaker(speaker);

        List<SpeakerDTO> allSpeakers = speakerService.getAllSpeakers();
        assertThat(allSpeakers.size()).isEqualTo(1);
        assertThat(allSpeakers.get(0).getBio()).isEqualTo("I am a speaker");
        assertThat(allSpeakers.get(0).getPhone()).isEqualTo("123456789");
        assertThat(allSpeakers.get(0).getUser().getUser_id()).isEqualTo(myUser.getUser_id());
        assertThat(allSpeakers.get(0).getUser().getFirstName()).isEqualTo(myUser.getFirstName());
        assertThat(allSpeakers.get(0).getUser().getLastName()).isEqualTo(myUser.getLastName());
        assertThat(allSpeakers.get(0).getUser().getOffice_function()).isEqualTo(myUser.getOffice_function());
    }

    @Test
    public void testIfICanUpdateAnExistingSpeaker() {
        makeSpeaker();
        List<SpeakerDTO> allSpeakers = speakerService.getAllSpeakers();
        assertThat(allSpeakers.size()).isEqualTo(1);
        SpeakerDTO speaker = allSpeakers.get(0);

        UpdateSpeakerDto updateSpeaker = new SpeakerController.UpdateSpeakerDto();
        updateSpeaker.setSpeakerId(speaker.getSpeaker_id());
        updateSpeaker.setBio("I am a speaker and I have been updated");
        updateSpeaker.setPhone("987654321");

        speakerService.updateSpeaker(updateSpeaker);

        allSpeakers = speakerService.getAllSpeakers();
        assertThat(allSpeakers.size()).isEqualTo(1);
        assertThat(allSpeakers.get(0).getBio()).isEqualTo("I am a speaker and I have been updated");
        assertThat(allSpeakers.get(0).getPhone()).isEqualTo("987654321");
    }

    @Test
    public void testIfICanDeleteASpeaker() {
        makeSpeaker();
        List<SpeakerDTO> allSpeakers = speakerService.getAllSpeakers();
        assertThat(allSpeakers.size()).isEqualTo(1);
        speakerService.deleteSpeaker(allSpeakers.get(0).getSpeaker_id());
        allSpeakers = speakerService.getAllSpeakers();
        assertThat(allSpeakers.size()).isEqualTo(0);
    }

    @Test
    public void testIfIGetASpeakerDTOWhenIAskForASpeakerById() {
        makeSpeaker();
        List<SpeakerDTO> allSpeakers = speakerService.getAllSpeakers();
        assertThat(allSpeakers.size()).isEqualTo(1);
        SpeakerDTO speaker = allSpeakers.get(0);
        SpeakerDTO fetchedSpeaker = speakerService.getSpeakerById(speaker.getSpeaker_id());
        assertThat(fetchedSpeaker.getSpeaker_id()).isEqualTo(speaker.getSpeaker_id());
        assertThat(fetchedSpeaker.getBio()).isEqualTo(speaker.getBio());
        assertThat(fetchedSpeaker.getPhone()).isEqualTo(speaker.getPhone());
        assertThat(fetchedSpeaker.getUser().getUser_id()).isEqualTo(speaker.getUser().getUser_id());
        assertThat(fetchedSpeaker.getUser().getFirstName()).isEqualTo(speaker.getUser().getFirstName());
        assertThat(fetchedSpeaker.getUser().getLastName()).isEqualTo(speaker.getUser().getLastName());
        assertThat(fetchedSpeaker.getUser().getOffice_function()).isEqualTo(speaker.getUser().getOffice_function());
    }

    @Test
    public void makeUser() {
        UserDTO user = new UserDTO();
        user.setUser_id(UUID.fromString("794c3104-1294-4660-a8f4-033bc67ce6ad"));
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setOffice_function("Developer");
        Role role = new Role();
        role.setRoleName(Roles.ORGANIZER);
        roleRepository.save(role);
        user.setRoles(List.of(role));
        userService.createUser(user);
        myUser = userRepository.findById(UUID.fromString("794c3104-1294-4660-a8f4-033bc67ce6ad"))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void makeSpeaker() {
        makeUser();
        MakeSpeakerDto speaker = new MakeSpeakerDto();
        speaker.setUserId(myUser.getUser_id());
        speaker.setBio("I am a speaker");
        speaker.setPhone("123456789");
        speakerService.createSpeaker(speaker);
    }
}
