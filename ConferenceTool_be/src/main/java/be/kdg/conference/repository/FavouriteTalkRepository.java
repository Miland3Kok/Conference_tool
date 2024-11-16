package be.kdg.conference.repository;

import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.FavouriteTalk;
import be.kdg.conference.model.eventmanagement.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FavouriteTalkRepository extends JpaRepository<FavouriteTalk, UUID> {

    Optional<FavouriteTalk> findByUserAndTalk(User user, Talk talk);


    @Query("SELECT COUNT(f) FROM FavouriteTalk f WHERE f.talk.talk_id = :talkId")
    int getAmountOfFavorites(UUID talkId);
}
