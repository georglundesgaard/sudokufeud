package no.lundesgaard.sudokufeud.api.mapper;

import no.lundesgaard.sudokufeud.api.model.JsonGame;
import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.constants.State;
import no.lundesgaard.sudokufeud.constants.Status;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Player;
import no.lundesgaard.sudokufeud.model.PlayerId;
import no.lundesgaard.sudokufeud.model.Profile;

import org.joda.time.DateTime;

public class JsonGameMapper {
	private String userId;

	public JsonGameMapper(String userId) {
		this.userId = userId;
	}

	public JsonGame from(Game game) {
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		Player player;
		Player opponent;
		if (player1.getProfile().getUserId().equals(userId)) {
			player = player1;
			opponent = player2;
		} else {
			player = player2;
			opponent = player1;
		}
		Profile opponentProfile = opponent != null ? opponent.getProfile() : null;

		long gameId = game.getId();
		int playerScore = player.getScore();
		int[] playerAvailablePieces = player.getAvailablePieces();
		String opponentUserId = opponentProfile != null ? opponentProfile.getUserId() : null;
		int opponentScore = opponent != null ? opponent.getScore() : 0;
		State state = game.getState();
		Status status = player == player1 ? game.getStatusForPlayer1() : game.getStatusForPlayer2();
		PlayerId currentPlayerId = game.getCurrentPlayer();
		String currentPlayer;
		if (currentPlayerId == null) {
			currentPlayer = null;
		} else if (currentPlayerId == PlayerId.PLAYER_ONE) {
			currentPlayer = player1.getProfile().getUserId();
		} else {
			currentPlayer = player2.getProfile().getUserId();
		} 
		Integer[] board = game.getBoardArray();
		Difficulty difficulty = game.getBoardDifficulty();
		DateTime created = new DateTime(game.getCreated().getTime());

		return new JsonGame(
				gameId,
				playerScore,
				playerAvailablePieces,
				opponentUserId,
				opponentScore,
				state,
				status,
				currentPlayer,
				board,
				difficulty,
				created);
	}
}
