package be.kdg.conference.model.eventmanagement;


import be.kdg.conference.controller.dto.RoomDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID room_id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    @JsonIgnore
    private Conference conference;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Talk> talks;

    public Room(String name) {
        this.room_id = UUID.randomUUID();
        this.name = name;
        this.talks = new ArrayList<>();
    }


    public Room(RoomDTO roomDTO, Conference conference) {
        this.name = roomDTO.getName();
        this.conference = conference;
        this.talks = new ArrayList<>();
    }



    public void addTalk(Talk talk) {
        this.talks.add(talk);
    }

    public void removeTalk(Talk talk) {
        this.talks.remove(talk);
    }

    public void clearTalks() {
        this.talks.clear();
    }
}
