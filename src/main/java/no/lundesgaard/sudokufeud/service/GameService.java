package no.lundesgaard.sudokufeud.service;

import static java.lang.String.format;

import java.util.List;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.constants.State;
import no.lundesgaard.sudokufeud.constants.Status;
import no.lundesgaard.sudokufeud.engine.BoardGenerator;
import no.lundesgaard.sudokufeud.model.Board;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.model.Player;
import no.lundesgaard.sudokufeud.model.PlayerId;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.GameRepository;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameService {

	@Autowired
	private BoardGenerator boardGenerator;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private GameRepository gameRepository;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private ProfileRepository profileRepository;

	/**
	 * @return game with a random player or an invitation for a random game if none found
	 */
	public long createGame(String userId, Difficulty difficulty) {
		Profile profile = profileRepository.findByUserId(userId);
		Game randomGame = gameRepository.findRandomGameWithoutSecondPlayerExludingGamesWithThisPlayer(profile.getId());
		if (randomGame == null) {
			return createGame(profile, null, difficulty);
		}
		Player player2 = new Player(profile);
		randomGame.setPlayer2(player2);
		randomGame.start();
		return randomGame.getId();
	}

	public long createGame(String userId1, String userId2, Difficulty difficulty) {
		Profile profile1 = profileRepository.findByUserId(userId1);
		Profile profile2 = profileRepository.findByUserId(userId2);
		return createGame(profile1, profile2, difficulty);
	}

	private long createGame(Profile profile1, Profile profile2, Difficulty difficulty) {
		Board board = boardGenerator.generateBoard(difficulty);
		Game game = Game.create(profile1, profile2, board);
		return gameRepository.save(game).getId();
	}

	public List<Game> getGames(String playerUserId) throws UnknownUserIdException {
		Profile player = profileRepository.findByUserId(playerUserId);
		return player.findGames();
	}

	public Game getGame(String playerUserId, long gameId) throws UnknownUserIdException {
		Profile player = profileRepository.findByUserId(playerUserId);
		return player.findGame(gameId);
	}

	public Game acceptInvitation(String playerUserId, long gameId) throws UnknownUserIdException, IllegalStateException {
		Profile player = profileRepository.findByUserId(playerUserId);
		Game game = player.findGame(gameId);
		if (game.getInvitedPlayerId() == player.getId() && game.getState() == State.NEW) {
			game.start();
			return game;
		}
		throw new IllegalStateException("not an invitation");
	}

	public void declineInvitation(String playerUserId, long gameId) throws UnknownUserIdException, IllegalStateException {
		Profile player = profileRepository.findByUserId(playerUserId);
		Game game = player.findGame(gameId);
		if (game.getInvitedPlayerId() == player.getId() && game.getState() == State.NEW) {
			gameRepository.delete(game.getId());
		}
		throw new IllegalStateException("not an invitation");
	}

	public int executeRound(String playerUserId, long gameId, Move[] moves) throws IllegalStateException {
		Profile playerProfile = profileRepository.findByUserId(playerUserId);
		Game game = playerProfile.findGame(gameId);

		Player currentPlayer;
		if (game.getCurrentPlayer() == PlayerId.PLAYER_ONE) {
			currentPlayer = game.getPlayer1();
		} else {
			currentPlayer = game.getPlayer2();
		}
		if (!currentPlayer.getProfile().getUserId().equals(playerUserId)) {
			throw new IllegalStateException(format("player <%s> is not current player", currentPlayer.getProfile().getUserId()));
		}

		Status status;
		if (game.getCurrentPlayer() == PlayerId.PLAYER_ONE) {
			status = game.getStatusForPlayer1();
		} else {
			status = game.getStatusForPlayer2();
		}

		if (status == Status.READY) {
			game.executeRound(moves);
			return game.getRounds().size() - 1;
		}

		throw new IllegalStateException(format("game with id <%s> is not running", gameId));
	}
}
