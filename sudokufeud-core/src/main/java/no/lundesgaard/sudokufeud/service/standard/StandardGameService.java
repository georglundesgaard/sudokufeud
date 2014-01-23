package no.lundesgaard.sudokufeud.service.standard;

import java.util.List;

import no.lundesgaard.sudokufeud.engine.BoardGenerator;
import no.lundesgaard.sudokufeud.engine.GameEngine;
import no.lundesgaard.sudokufeud.model.Board;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.repository.GameRepository;
import no.lundesgaard.sudokufeud.service.GameService;
import no.lundesgaard.sudokufeud.service.IllegalGameStateException;

public class StandardGameService implements GameService {

    private final BoardGenerator boardGenerator;
    private final GameEngine gameEngine;
    private final GameRepository gameRepository;

    public StandardGameService(BoardGenerator boardGenerator, GameEngine gameEngine, GameRepository gameRepository) {
        this.boardGenerator = boardGenerator;
        this.gameEngine = gameEngine;
        this.gameRepository = gameRepository;
    }

    @Override
    public String createGame(String playerId1, String playerId2, Board.Difficulty difficulty) {
        // TODO: validate player ids

        Board board = boardGenerator.generateBoard(difficulty);
        Game game = gameEngine.createGame(playerId1, playerId2, board);
        return gameRepository.create(game);
    }

    @Override
    public List<Game> getGames(String playerId) {
        return gameRepository.findAllByPlayerId(playerId);
    }

    @Override
    public Game getGame(String playerId, String gameId) {
        return gameRepository.findOneByPlayerId(playerId, gameId);
    }

    @Override
    public Game acceptInvitation(String playerId, String gameId) {
        Game game = gameRepository.findOneByPlayerId(playerId, gameId);
        if (game.getState() != Game.State.NEW || !game.getPlayer2().getPlayerId().equals(playerId)) {
            throw new IllegalGameStateException("not an invitation");
        }
        game = gameEngine.startGame(game, game.getPlayer1().getPlayerId());
        gameRepository.update(game);
        return game;
    }

    @Override
    public void declineInvitation(String playerId, String gameId) {
        Game game = gameRepository.findOneByPlayerId(playerId, gameId);
        if (game.getState() != Game.State.NEW || !game.getPlayer2().getPlayerId().equals(playerId)) {
            throw new IllegalGameStateException("not an invitation");
        }
        gameRepository.delete(game.getId());
    }

    @Override
    public int executeRound(String playerId, String gameId, Move[] moves) {
        Game game = gameRepository.findOneByPlayerId(playerId, gameId);
        if (game.getState() != Game.State.RUNNING) {
            throw new IllegalGameStateException("not running");
        }
        if (!game.getCurrentPlayer().getPlayerId().equals(playerId)) {
            throw new IllegalGameStateException("not current player");
        }

        game = gameEngine.executeRound(game, moves);
        gameRepository.update(game);

        return game.getRounds().length - 1;
    }
}
