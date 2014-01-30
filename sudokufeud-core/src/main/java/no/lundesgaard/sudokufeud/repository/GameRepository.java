package no.lundesgaard.sudokufeud.repository;

import java.util.List;

import no.lundesgaard.sudokufeud.model.Game;

public interface GameRepository extends Repository<Game> {
    List<Game> findAllByPlayerId(String playerId);

    Game findOneByPlayerId(String playerId, String gameId);
}
