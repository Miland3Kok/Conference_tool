package be.kdg.conference.service.implementation;

import be.kdg.conference.model.account.User;
import be.kdg.conference.model.eventmanagement.FavouriteTalk;
import be.kdg.conference.model.eventmanagement.Talk;
import be.kdg.conference.repository.FavouriteTalkRepository;
import be.kdg.conference.service.FavouriteTalkService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FavouriteTalkServiceImpl implements FavouriteTalkService {

    private final FavouriteTalkRepository favouriteTalkRepository;

    public FavouriteTalkServiceImpl(FavouriteTalkRepository favouriteTalkRepository) {
        this.favouriteTalkRepository = favouriteTalkRepository;
    }

    @Override
    public void saveFavouriteTalk(FavouriteTalk favouriteTalk) {
        favouriteTalkRepository.save(favouriteTalk);
    }

    @Override
    public Optional<FavouriteTalk> findByUserAndTalk(User user, Talk talk) {
        return favouriteTalkRepository.findByUserAndTalk(user, talk);
    }

    @Override
    public void delete(FavouriteTalk favouriteTalk) {
        favouriteTalkRepository.delete(favouriteTalk);
    }

    @Override
    public int getAmountOfFavorites(UUID talkId) {
        return favouriteTalkRepository.getAmountOfFavorites(talkId);
    }
}
