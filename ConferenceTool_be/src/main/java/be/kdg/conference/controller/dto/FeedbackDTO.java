package be.kdg.conference.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class FeedbackDTO {
    @Min(1)
    @Max(5)
    private int rating;
    private String comment;
    private String talkId;
    private String userId;
}
