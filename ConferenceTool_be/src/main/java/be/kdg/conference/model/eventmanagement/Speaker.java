package be.kdg.conference.model.eventmanagement;

import be.kdg.conference.model.account.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter @Setter
@Entity(name = "speaker")
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID speaker_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(nullable = false, length = 200)
    private String bio;

    @Column(nullable = false, length = 50)
    private String phone;

    public Speaker(User user, String bio, String phone) {
        this.user = user;
        this.bio = bio;
        this.phone = phone;
    }
}
