package be.kdg.conference.model.eventmanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity(name = "conference")
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID conference_id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Column(nullable = false)
    private LocalDateTime end_date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @JsonIgnore
    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @Column(nullable = false)
    private boolean active;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] floorPlanImage;

    public Conference(String name, String description, LocalDateTime start_date, LocalDateTime end_date, Location location, boolean active) {
        this.conference_id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
        this.rooms = new ArrayList<Room>();
        this.active = active;
        this.floorPlanImage = null;
    }

    public Conference(UUID id, String name, String description, LocalDateTime start_date, LocalDateTime end_date, Location location, boolean active) {
        this.conference_id = id;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
        this.rooms = new ArrayList<Room>();
        this.active = active;
        this.floorPlanImage = null;
    }

    public Conference(String name, String description, String start_date, String end_date, Location location, boolean active) {
        this.conference_id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.start_date = LocalDateTime.parse(start_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end_date = LocalDateTime.parse(end_date, DateTimeFormatter.ISO_DATE_TIME);
        this.location = location;
        this.rooms = new ArrayList<Room>();
        this.active = active;
        this.floorPlanImage = null;
    }
}
