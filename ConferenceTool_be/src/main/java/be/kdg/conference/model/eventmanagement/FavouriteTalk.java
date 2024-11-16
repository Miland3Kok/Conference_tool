package be.kdg.conference.model.eventmanagement;

import be.kdg.conference.model.account.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class FavouriteTalk {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID favourite_talk_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "talk_id")
    private Talk talk;

    public FavouriteTalk(User user, Talk talk) {
        this.user = user;
        this.talk = talk;
    }
}
