package be.kdg.conference.service;

import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.FavouriteTalk;
import be.kdg.conference.model.eventmanagement.Talk;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface FavouriteTalkService {
    void saveFavouriteTalk(FavouriteTalk favouriteTalk);

    Optional<FavouriteTalk> findByUserAndTalk(User user, Talk talk);

    void delete(FavouriteTalk favouriteTalk);

    int getAmountOfFavorites(UUID talkId);
}
