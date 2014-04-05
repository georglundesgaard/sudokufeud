package no.lundesgaard.sudokufeud.service.standard;

import static java.lang.String.format;

import java.util.List;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.constants.Status;
import no.lundesgaard.sudokufeud.engine.BoardGenerator;
import no.lundesgaard.sudokufeud.engine.GameEngine;
import no.lundesgaard.sudokufeud.model.Board;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.GameRepository;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;
import no.lundesgaard.sudokufeud.service.GameService;
import no.lundesgaard.sudokufeud.service.IllegalGameStateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardGameService implements GameService {

	@Autowired
	private BoardGenerator boardGenerator;

	@Autowired
	private GameEngine gameEngine;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public String createGame(String playerUserId1, String playerUserId2, Difficulty difficulty) {
		Profile player1 = profileRepository.findByUserId(playerUserId1);
		Profile player2 = profileRepository.findByUserId(playerUserId2);

		Board board = boardGenerator.generateBoard(difficulty);
		Game game = gameEngine.createGame(player1, player2, board);
		game = gameRepository.create(game);

		return game.getId();
	}

	@Override
	public List<Game> getGames(String playerUserId) throws UnknownUserIdException {
		Profile player = profileRepository.findByUserId(playerUserId);
		return gameRepository.findAllByPlayer(player);
	}

	@Override
	public Game getGame(String playerUserId, String gameId) throws UnknownUserIdException {
		Profile player = profileRepository.findByUserId(playerUserId);
		return gameRepository.findOneByPlayer(player, gameId);
	}

	@Override
	public Game acceptInvitation(String playerUserId, String gameId) throws UnknownUserIdException, IllegalGameStateException {
		Profile player = profileRepository.findByUserId(playerUserId);
		Game game = gameRepository.findOneByPlayer(player, gameId);
		if (game.getInvitedPlayerId().equals(player.getId())) {
			Profile startingPlayer = profileRepository.read(game.getPlayerId1());
			game = gameEngine.startGame(game, startingPlayer);
			gameRepository.update(game);
			return game;
		}

		throw new IllegalGameStateException("not an invitation");
	}

	@Override
	public void declineInvitation(String playerUserId, String gameId) throws UnknownUserIdException, IllegalGameStateException {
		Profile player = profileRepository.findByUserId(playerUserId);
		Game game = gameRepository.findOneByPlayer(player, gameId);
		if (game.getInvitedPlayerId().equals(player.getId())) {
			gameRepository.delete(game.getId());
		}

		throw new IllegalGameStateException("not an invitation");
	}

	@Override
	public int executeRound(String playerUserId, String gameId, Move[] moves) throws IllegalGameStateException {
		Profile player = profileRepository.findByUserId(playerUserId);
		Game game = gameRepository.findOneByPlayer(player, gameId);

		if (!player.getId().equals(game.getCurrentPlayerId())) {
			throw new IllegalGameStateException(format("player <%s> is not current player", player.getUserId()));
		}

		Status status;
		if (game.getPlayerId1().equals(player.getId())) {
			status = game.getStatusForPlayer1();
		} else {
			status = game.getStatusForPlayer2();
		}

		if (status == Status.READY) {
			game = gameEngine.executeRound(game, moves);
			gameRepository.update(game);
			return game.getRounds().length - 1;
		}

		throw new IllegalGameStateException(format("game with id <%s> is not running", gameId));
	}
}
