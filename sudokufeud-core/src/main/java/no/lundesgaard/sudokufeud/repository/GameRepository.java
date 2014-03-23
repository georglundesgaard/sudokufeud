package no.lundesgaard.sudokufeud.repository;

import java.util.List;

import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Profile;

public interface GameRepository extends Repository<Game> {
	List<Game> findAllByPlayer(Profile player);

	Game findOneByPlayer(Profile player, String gameId);
}
