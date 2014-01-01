package no.lundesgaard.sudokufeud.repository;

import no.lundesgaard.sudokufeud.model.Game;

import java.util.List;

public interface GameRepository extends Repository<Game> {
    List<Game> findAllByPlayerId(String playerId);

    Game findOneByPlayerId(String playerId, String gameId);
}
