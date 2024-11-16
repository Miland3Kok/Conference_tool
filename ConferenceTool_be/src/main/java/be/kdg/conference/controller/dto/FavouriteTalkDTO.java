package be.kdg.conference.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FavouriteTalkDTO {
    private String user_id;
    private String talk_id;
}
