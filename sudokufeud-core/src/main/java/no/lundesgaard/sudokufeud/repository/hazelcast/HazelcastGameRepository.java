package no.lundesgaard.sudokufeud.repository.hazelcast;

import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.repository.GameRepository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.GameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class HazelcastGameRepository
        extends AbstractHazelcastRepository<Game>
        implements GameRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastGameRepository.class);

    private final IMap<String, Set<String>> playerIdGameMap;

    public HazelcastGameRepository(ILock gameRepositorylock, IMap<String, Game> gameMap, IMap<String, Set<String>> playerIdGameMap) {
        super(LOGGER, gameRepositorylock, gameMap);
        this.playerIdGameMap = playerIdGameMap;
    }

    @Override
    public List<Game> findAllByPlayerId(String playerId) {
        if (!playerIdGameMap.containsKey(playerId)) {
            return Collections.emptyList();
        }

        Set<String> gameIdSet = playerIdGameMap.get(playerId);
        List<Game> games = new ArrayList<>();
        for (String gameId : gameIdSet) {
            games.add(read(gameId));
        }

        return games;
    }

    @Override
    public Game findOneByPlayerId(String playerId, String gameId) {
        if (playerIdGameMap.containsKey(playerId)) {
            Set<String> gameIdSet = playerIdGameMap.get(playerId);
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
        playerIdGameMap.put(playerId, gameIdList);
    }

    private void removeGameId(String playerId, String gameId) {
        Set<String> gameIdSet = getGameIdSet(playerId);
        gameIdSet.remove(gameId);
        playerIdGameMap.put(playerId, gameIdSet);
    }

    private Set<String> getGameIdSet(String profileId) {
        Set<String> gameIdSet = playerIdGameMap.get(profileId);
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
