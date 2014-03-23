package no.lundesgaard.sudokufeud.api.mapper;

import no.lundesgaard.sudokufeud.api.model.JsonGame;
import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.constants.State;
import no.lundesgaard.sudokufeud.constants.Status;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Player;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.joda.time.DateTime;

public class JsonGameMapper {
	private String userId;
	private ProfileService profileService;

	public JsonGameMapper(String userId, ProfileService profileService) {
		this.userId = userId;
		this.profileService = profileService;
	}

	public JsonGame from(Game game) {
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		Profile playerProfile = profileService.getProfileByUserId(userId);
		Player player;
		Player opponent;
		if (player1.getPlayerId().equals(playerProfile.getId())) {
			player = player1;
			opponent = player2;
		} else {
			player = player2;
			opponent = player1;
		}
		Profile opponentProfile = profileService.getProfile(opponent.getPlayerId());

		String gameId = game.getId();
		int playerScore = player.getScore();
		int[] playerAvailablePieces = player.getAvailablePieces();
		String opponentUserId = opponentProfile.getUserId();
		int opponentScore = opponent.getScore();
		State state = game.getState();
		Status status = player == player1 ? game.getStatusForPlayer1() : game.getStatusForPlayer2();
		String currentPlayerId = game.getCurrentPlayerId();
		String currentPlayer = currentPlayerId != null ? profileService.getProfile(currentPlayerId).getUserId() : null;
		Integer[] board = game.getBoardArray();
		Difficulty difficulty = game.getBoardDifficulty();
		DateTime created = game.getCreated();

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
