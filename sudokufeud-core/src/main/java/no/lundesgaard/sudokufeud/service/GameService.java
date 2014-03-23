package no.lundesgaard.sudokufeud.service;

import java.util.List;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;

public interface GameService {
	List<Game> getGames(String playerUserId);

	Game getGame(String playerUserId, String gameId);

	String createGame(String playerUserId1, String playerUserId2, Difficulty difficulty);

	Game acceptInvitation(String playerUserId, String gameId);

	void declineInvitation(String playerUserId, String gameId);

	int executeRound(String playerUserId, String gameId, Move[] moves);
}
