package be.kdg.conference.model.eventmanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity(name = "talk")
@Getter @Setter
public class Talk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID talk_id;

    @Column(nullable = false, length = 50)
    private String title;

    private String description;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @ManyToMany
    @JoinTable(
            name = "talk_speaker",
            joinColumns = @JoinColumn(name = "talk_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private List<Speaker> speakers;

    @OneToMany(mappedBy = "talk")
    private List<FavouriteTalk> favouritedBy = new ArrayList<>();

    public Talk(String title, String description, String start_date, String end_date, Room room) {
        this.title = title;
        this.description = description;
        this.start_date = LocalDateTime.parse(start_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end_date =LocalDateTime.parse(end_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.room = room;
    }


}
