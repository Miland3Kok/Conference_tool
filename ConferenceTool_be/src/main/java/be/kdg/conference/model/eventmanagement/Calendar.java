package be.kdg.conference.model.eventmanagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID calendar_id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Talk> talks;
}
