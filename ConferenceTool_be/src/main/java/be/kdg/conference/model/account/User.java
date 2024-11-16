package be.kdg.conference.model.account;

import be.kdg.conference.model.event_interaction.questions.UserAnswer;
import be.kdg.conference.model.eventmanagement.FavouriteTalk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity(name = "users")
public class User {
    @Id
    private UUID user_id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String office_function;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Role> roles;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] profilePicture;

    @OneToMany(mappedBy = "user")
    private List<FavouriteTalk> favouriteTalks;

    @OneToMany(mappedBy = "user")
    private List<UserAnswer> userAnswers;

    public User(String firstName, String lastName, String office_function) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.office_function = office_function;
        this.roles = new ArrayList<>();
        this.profilePicture = null;
        this.favouriteTalks = new ArrayList<>();
    }
}
