/*package be.kdg.conference.controller.service;

import be.kdg.conference.model.account.Role;
import be.kdg.conference.model.account.Roles;
import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Location;
import be.kdg.conference.model.eventmanagement.Speaker;
import be.kdg.conference.repository.*;
import be.kdg.conference.service.implementation.MessageServiceImpl;
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
public class MessageServiceTest {

    @Autowired
    MessageServiceImpl messageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SpeakerRepository speakerRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    LocationRepository locationRepository;

    private UUID speakerId;

    @AfterEach
    public void after() {
        messageRepository.deleteAll();
        speakerRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        conferenceRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    public void testIfICanMakeAMessageForOrganisationAndReceiveTheMessageForOrganisation() {
        makeOrganiser();
        makeSpeaker();
        makeConferences();

        //messageService.createMessageForOrganisation(speakerId, "Hello", conferenceRepository.findAll().get(0).getConference_id());

        assertThat(messageRepository.findAll().size()).isEqualTo(1);
        assertThat(messageRepository.findAll().get(0).getMessage()).isEqualTo("Hello");
        assertThat(messageRepository.findAll().get(0).isForOrganisation()).isTrue();

        assertThat(messageService.getMessagesForOrganisation().size()).isEqualTo(1);
        assertThat(messageService.getMessagesForOrganisation().get(0).getMessage()).isEqualTo("Hello");
        assertThat(messageService.getMessagesForOrganisation().get(0).isForOrganisation()).isTrue();
    }

    @Test
    public void testIfICanMakeAMessageForTalkAndReceiveIt() {
        makeOrganiser();
        makeSpeaker();
        makeConferences();

        //messageService.createMessageForTalk(speakerId, conferenceRepository.findAll().get(0).getConference_id(), "Hello");

        assertThat(messageRepository.findAll().size()).isEqualTo(1);
        assertThat(messageRepository.findAll().get(0).getMessage()).isEqualTo("Hello");
        assertThat(messageRepository.findAll().get(0).isForOrganisation()).isFalse();

        assertThat(messageService.getMessagesForTalk(conferenceRepository.findAll().get(0).getConference_id()).size()).isEqualTo(1);
        assertThat(messageService.getMessagesForTalk(conferenceRepository.findAll().get(0).getConference_id()).get(0).getMessage()).isEqualTo("Hello");
        assertThat(messageService.getMessagesForTalk(conferenceRepository.findAll().get(0).getConference_id()).get(0).isForOrganisation()).isFalse();
    }

    private void makeOrganiser() {
        User user = new User();
        user.setUser_id(UUID.fromString("794c3104-1294-4660-a8f4-033bc67ce6ad"));
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setOffice_function("Organiser");
        Role role = new Role();
        role.setRoleName(Roles.ORGANIZER);
        roleRepository.save(role);
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    private void makeSpeaker() {
        User user = new User();
        user.setUser_id(UUID.fromString("794c3104-1294-4660-a8f4-033bc67ce7ad"));
        user.setFirstName("Speaker1");
        user.setLastName("Speaker1");
        user.setOffice_function("Developer");
        Role role = new Role();
        role.setRoleName(Roles.SPEAKER);
        roleRepository.save(role);
        user.setRoles(List.of(role));
        userRepository.save(user);

        Speaker speaker = new Speaker();
        speaker.setUser(user);
        speaker.setBio("I am a speaker");
        speaker.setPhone("123456789");
        speakerRepository.save(speaker);

        speakerId = speakerRepository.findAll().get(0).getSpeaker_id();
    }

    private void makeConferences() {
        List<Location> locations = makeLocations();
        Conference conference = new Conference("Java Conference", "A conference about Java",
                "2022-12-12T12:00:00", "2022-12-12T18:00:00", locations.get(0), false);
        conferenceRepository.save(conference);

        conference = new Conference("Python Conference", "A conference about Python",
                "2022-12-12T12:00:00", "2022-12-12T18:00:00", locations.get(1), false);

        conferenceRepository.save(conference);
    }

    private List<Location> makeLocations() {
        Location location = new Location();
        location.setCity("Antwerp");
        location.setCountry("Belgium");
        location.setPostal_code("2000");
        location.setStreet("Karel One");
        location.setNumber("22B");
        location.setExtraInfo("Room 1");
        locationRepository.save(location);

        Location location2 = new Location();
        location2.setCity("Brussels");
        location2.setCountry("Belgium");
        location2.setPostal_code("3000");
        location2.setStreet("Karel On Brussels ");
        location2.setNumber("22A");
        location2.setExtraInfo("Room 2");
        locationRepository.save(location2);
        return locationRepository.findAll();
    }
}
*/