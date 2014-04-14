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
import no.lundesgaard.sudokufeud.model.Player;
import no.lundesgaard.sudokufeud.model.PlayerId;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.GameRepository;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;
import no.lundesgaard.sudokufeud.service.GameService;
import no.lundesgaard.sudokufeud.service.IllegalGameStateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StandardGameService implements GameService {

	@Autowired
	private BoardGenerator boardGenerator;

	@Autowired
	private GameEngine gameEngine;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private GameRepository gameRepository;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public long createGame(String playerUserId1, String playerUserId2, Difficulty difficulty) {
		Profile player1 = profileRepository.findByUserId(playerUserId1);
		Profile player2 = profileRepository.findByUserId(playerUserId2);

		Board board = boardGenerator.generateBoard(difficulty);
		Game game = gameEngine.createGame(player1, player2, board);
		game = gameRepository.save(game);

		return game.getId();
	}

	@Override
	public List<Game> getGames(String playerUserId) throws UnknownUserIdException {
		Profile player = profileRepository.findByUserId(playerUserId);
		return player.findGames();
	}

	@Override
	public Game getGame(String playerUserId, long gameId) throws UnknownUserIdException {
		Profile player = profileRepository.findByUserId(playerUserId);
		return player.findGame(gameId);
	}

	@Override
	public Game acceptInvitation(String playerUserId, long gameId) throws UnknownUserIdException, IllegalGameStateException {
		Profile player = profileRepository.findByUserId(playerUserId);
		Game game = player.findGame(gameId);
		if (game.getInvitedPlayerId() == player.getId()) {
			String startingPlayerUserId = game.getPlayer1().getProfile().getUserId();
			return gameEngine.startGame(game, startingPlayerUserId);
		}

		throw new IllegalGameStateException("not an invitation");
	}

	@Override
	public void declineInvitation(String playerUserId, long gameId) throws UnknownUserIdException, IllegalGameStateException {
		Profile player = profileRepository.findByUserId(playerUserId);
		Game game = player.findGame(gameId);
		if (game.getInvitedPlayerId() == player.getId()) {
			gameRepository.delete(game.getId());
		}

		throw new IllegalGameStateException("not an invitation");
	}

	@Override
	public int executeRound(String playerUserId, long gameId, Move[] moves) throws IllegalGameStateException {
		Profile playerProfile = profileRepository.findByUserId(playerUserId);
		Game game = playerProfile.findGame(gameId);

		Player currentPlayer;
		if (game.getCurrentPlayer() == PlayerId.PLAYER_ONE) {
			currentPlayer = game.getPlayer1();
		} else {
			currentPlayer = game.getPlayer2();
		}
		if (!currentPlayer.getProfile().getUserId().equals(playerUserId)) {
			throw new IllegalGameStateException(format("player <%s> is not current player", currentPlayer.getProfile().getUserId()));
		}

		Status status;
		if (game.getCurrentPlayer() == PlayerId.PLAYER_ONE) {
			status = game.getStatusForPlayer1();
		} else {
			status = game.getStatusForPlayer2();
		}

		if (status == Status.READY) {
			game = gameEngine.executeRound(game, moves);
			return game.getRounds().size() - 1;
		}

		throw new IllegalGameStateException(format("game with id <%s> is not running", gameId));
	}
}
