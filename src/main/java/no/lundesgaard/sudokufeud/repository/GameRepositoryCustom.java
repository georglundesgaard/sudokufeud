package no.lundesgaard.sudokufeud.repository;

import no.lundesgaard.sudokufeud.model.Game;

public interface GameRepositoryCustom {
	Game findRandomGameWithoutSecondPlayerExludingGamesWithThisPlayer(long playerProfileId);
}
