package no.lundesgaard.sudokufeud.repository.hazelcast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.repository.GameRepository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.GameNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hazelcast.core.IMap;

@Repository
public class HazelcastGameRepository
        extends AbstractHazelcastRepository<Game>
        implements GameRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastGameRepository.class);
    private static final String GAME_REPOSITORY_LOCK_ID = "gameRepositoryLock";
    private static final String GAME_REPOSITORY_MAP_ID = "gameRepositoryMap";
    
    private static final String PLAYER_ID_GAME_IDS_MAP_ID = "playerIdGameIdsMap";
    
    public HazelcastGameRepository() {
        super(LOGGER, GAME_REPOSITORY_LOCK_ID, GAME_REPOSITORY_MAP_ID);
    }
    
    private IMap<String, Set<String>> getPlayerIdGameIdsMap() {
        return getHazelcastInstance().getMap(PLAYER_ID_GAME_IDS_MAP_ID);
    }

    @Override
    public List<Game> findAllByPlayerId(String playerId) {
        if (!getPlayerIdGameIdsMap().containsKey(playerId)) {
            return Collections.emptyList();
        }

        Set<String> gameIdSet = getPlayerIdGameIdsMap().get(playerId);
        List<Game> games = new ArrayList<>();
        for (String gameId : gameIdSet) {
            games.add(read(gameId));
        }

        return games;
    }

    @Override
    public Game findOneByPlayerId(String playerId, String gameId) {
        if (getPlayerIdGameIdsMap().containsKey(playerId)) {
            Set<String> gameIdSet = getPlayerIdGameIdsMap().get(playerId);
            for (String id : gameIdSet) {
                if (id.equals(gameId)) {
                    return read(gameId);
                }
            }
        }

        throw new GameNotFoundException(gameId, playerId);
    }

    @Override
    protected void onCreated(Game newGame) {
        String gameId = newGame.getId();
        String player1 = newGame.getPlayer1().getPlayerId();
        String player2 = newGame.getPlayer2().getPlayerId();

        addGameId(player1, gameId);
        addGameId(player2, gameId);
    }

    @Override
    protected void onUpdated(Game oldGame, Game newGame) {
        // do nothing for now
    }

    @Override
    protected void onDeleted(Game oldGame) {
        String gameId = oldGame.getId();
        String player1 = oldGame.getPlayer1().getPlayerId();
        String player2 = oldGame.getPlayer2().getPlayerId();

        removeGameId(player1, gameId);
        removeGameId(player2, gameId);
    }

    private void addGameId(String playerId, String gameId) {
        Set<String> gameIdList = getGameIdSet(playerId);
        gameIdList.add(gameId);
        getPlayerIdGameIdsMap().put(playerId, gameIdList);
    }

    private void removeGameId(String playerId, String gameId) {
        Set<String> gameIdSet = getGameIdSet(playerId);
        gameIdSet.remove(gameId);
        getPlayerIdGameIdsMap().put(playerId, gameIdSet);
    }

    private Set<String> getGameIdSet(String profileId) {
        Set<String> gameIdSet = getPlayerIdGameIdsMap().get(profileId);
        if (gameIdSet == null) {
            gameIdSet = new HashSet<>();
        }
        return gameIdSet;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(String id) {
        return new GameNotFoundException(id);
    }
}
