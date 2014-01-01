package no.lundesgaard.sudokufeud.service;

import no.lundesgaard.sudokufeud.model.Board;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;

import java.util.List;

public interface GameService {
    List<Game> getGames(String playerId);

    Game getGame(String playerId, String gameId);

    String createGame(String playerId1, String playerId2, Board.Difficulty difficulty);

    Game acceptInvitation(String playerId, String gameId);

    void declineInvitation(String playerId, String gameId);

    int executeRound(String playerId, String gameId, Move[] moves);
}
