package be.kdg.conference.controller.service;

import be.kdg.conference.controller.dto.MakeTalkDTO;
import be.kdg.conference.controller.dto.TalkDTO;
import be.kdg.conference.model.account.Role;
import be.kdg.conference.model.account.Roles;
import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.Conference;
import be.kdg.conference.model.eventmanagement.Location;
import be.kdg.conference.model.eventmanagement.Room;
import be.kdg.conference.model.eventmanagement.Speaker;
import be.kdg.conference.repository.*;
import be.kdg.conference.service.TalkService;
import jakarta.transaction.Transactional;
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
public class TalkServiceTest {

    /*

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TalkRepository talkRepository;

    @Autowired
    private TalkService talkService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    public void after() {
        talkRepository.deleteAll();
        conferenceRepository.deleteAll();
        locationRepository.deleteAll();
        speakerRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testIfICanMakeATalk() {
        makeTalk();

        List<Conference> conferences = conferenceRepository.findAll();
        assertThat(conferences.size()).isEqualTo(2);

        List<TalkDTO> talksList = talkService.getAllTalksForConference(conferences.get(0).getConference_id());

        assertThat(talksList.size()).isEqualTo(1);
        assertThat(talksList.get(0).getTitle()).isEqualTo("Java Talk");
        assertThat(talksList.get(0).getDescription()).isEqualTo("A talk about Java");

        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getTitle()).isEqualTo("Java Talk");
        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getDescription()).isEqualTo("A talk about Java");
        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getSpeakers().size()).isEqualTo(1);
        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getSpeakers().get(0).getBio()).isEqualTo("I am speaker 0");
    }

    @Test
    @Transactional
    public void TestToAddSpeakerToTalk() {
        makeTalk();

        List<TalkDTO> talksList = talkService.getAllTalksForConference(conferenceRepository.findAll().get(0).getConference_id());
        UUID speakerId = speakerRepository.findAll().get(1).getSpeaker_id();
        talkService.addSpeakerToTalk(talksList.get(0).getTalk_id(), speakerId);

        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getSpeakers().size()).isEqualTo(2);

        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getSpeakers().get(1).getBio()).isEqualTo("I am speaker 1");
    }

    @Test
    @Transactional
    public void TestToDeleteSpeakerFromTalk() {
        makeTalk();

        List<TalkDTO> talksList = talkService.getAllTalksForConference(conferenceRepository.findAll().get(0).getConference_id());
        UUID speakerId = speakerRepository.findAll().get(1).getSpeaker_id();
        talkService.addSpeakerToTalk(talksList.get(0).getTalk_id(), speakerId);

        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getSpeakers().size()).isEqualTo(2);

        talkService.removeSpeakerFromTalk(talksList.get(0).getTalk_id(), speakerId);

        assertThat(talkService.getTalk(talksList.get(0).getTalk_id())
                .getSpeakers().size()).isEqualTo(1);
    }

    private void makeTalk() {
        makeSpeakers();
        makeConferences();

        List<Speaker> speakers = speakerRepository.findAll();

        MakeTalkDTO makeTalkDTO = new MakeTalkDTO();
        makeTalkDTO.setTitle("Java Talk");
        makeTalkDTO.setDescription("A talk about Java");
        makeTalkDTO.setStart_date("2022-12-12T12:00:00");
        makeTalkDTO.setEnd_date("2022-12-12T18:00:00");
        makeTalkDTO.setSpeakers_id(List.of(speakers.get(0).getSpeaker_id()));
        makeTalkDTO.setRoom(makeRoom(UUID.fromString("794c3104-1294-4661-a8f4-033bc67ce6ad")).getRoom_id());

        talkService.createTalk(makeTalkDTO);
    }

    private User makeUser(UUID userId) {
        User user = new User();
        user.setUser_id(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setOffice_function("Developer");
        Role role = new Role();
        role.setRoleName(Roles.ORGANIZER);
        roleRepository.save(role);
        user.setRoles(List.of(role));
        userRepository.saveAndFlush(user);
        return user;
    }

    private Room makeRoom(UUID roomId) {
        Room room = new Room();
        room.setRoom_id(roomId);
        room.setName("Room 1");
        room.setConference(conferenceRepository.findAll().get(0));
        roomRepository.save(room);
        return room;
    }

    private void makeSpeakers() {
        for(int i = 0; i < 3; i++) {
            User user = makeUser(UUID.fromString("794c3104-1294-466" + i + "-a8f4-033bc67ce6ad"));
            Speaker speaker = new Speaker();
            speaker.setUser(user);
            speaker.setBio("I am speaker " + i);
            speaker.setPhone("123456789");
            speakerRepository.save(speaker);
        }
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

     */
}
