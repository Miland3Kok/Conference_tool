package be.kdg.conference.model.eventmanagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID message_id;

    private String subject;

    private String message;

    private boolean important;

    @ManyToOne
    @JoinColumn(name = "speaker_id")
    private Speaker speaker;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    private boolean forOrganisation;

    public Message(String message, Speaker speaker, Conference conference, boolean forOrganisation) {
        this.message = message;
        this.speaker = speaker;
        this.conference = conference;
        this.forOrganisation = forOrganisation;
    }
}
