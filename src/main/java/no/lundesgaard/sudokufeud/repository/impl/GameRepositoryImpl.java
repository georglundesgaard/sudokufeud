package no.lundesgaard.sudokufeud.repository.impl;

import java.util.List;
import java.util.Random;

import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.repository.GameRepository;
import no.lundesgaard.sudokufeud.repository.GameRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class GameRepositoryImpl implements GameRepositoryCustom {
	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private GameRepository gameRepository;
	
	@Override
	public Game findRandomGameWithoutSecondPlayerExludingGamesWithThisPlayer(long playerProfileId) {
		List<Game> games = gameRepository.findByPlayer2IsNull();
		int count = games.size();
		int skipGameCount = count == 0 ? 0 : new Random(System.currentTimeMillis()).nextInt(count);
		Game randomGame = null;
		for (Game game : games) {
			if (game.getPlayer1().getProfile().getId() != playerProfileId) {
				randomGame = game;
			}
			if (skipGameCount-- <= 0 && randomGame != null) {
				break;
			}
		}
		return randomGame;
	}
}
