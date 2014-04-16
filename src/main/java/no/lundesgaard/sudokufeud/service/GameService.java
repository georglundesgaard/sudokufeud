package no.lundesgaard.sudokufeud.service;

import java.util.List;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;

public interface GameService {
	List<Game> getGames(String playerUserId);
	Game getGame(String playerUserId, long gameId);
	long createGame(String playerUserId1, String playerUserId2, Difficulty difficulty);
	Game acceptInvitation(String playerUserId, long gameId);
	void declineInvitation(String playerUserId, long gameId);
	int executeRound(String playerUserId, long gameId, Move[] moves);
}
