package no.lundesgaard.sudokufeud.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.lundesgaard.sudokufeud.model.Board;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.model.Player;
import no.lundesgaard.sudokufeud.model.PlayerId;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.model.Round;

import org.springframework.stereotype.Service;

@Service
public class GameEngine {

	public Game startGame(Game game, String playerUserId) {
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		Board board = game.getBoard();

		int[] availablePieces = new int[7];
		System.arraycopy(board.getAvailablePieces(), 0, availablePieces, 0, 7);
		player1.setAvailablePieces(availablePieces);

		availablePieces = new int[7];
		System.arraycopy(board.getAvailablePieces(), 7, availablePieces, 0, 7);
		player2.setAvailablePieces(availablePieces);

		availablePieces = new int[board.getAvailablePieces().length - 14];
		game.setAvailablePieces(availablePieces);

		
		if (player1.getProfile().getUserId().equals(playerUserId)) {
			game.setCurrentPlayer(PlayerId.PLAYER_ONE);
		} else {
			game.setCurrentPlayer(PlayerId.PLAYER_TWO);
		}
		
		game.setStarted(new Date());

		return game;
	}

	public Game createGame(Profile playerProfile1, Profile playerProfile2, Board board) {
		if (playerProfile1 == null) {
			throw new GameEngineException("player1 required");
		}
		if (playerProfile2 == null) {
			throw new GameEngineException("player2 required");
		}
		if (board == null) {
			throw new GameEngineException("board required");
		}
		return new Game(new Player(playerProfile1), new Player(playerProfile2), board);
	}

	public Game executeRound(Game game, Move... movesInRound) {
		Board board = game.getBoard();
		Board.Statistics statisticsBefore = board.getStatistics();
		List<Integer> playerPieceList = new ArrayList<>();

		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();

		if (game.getCurrentPlayer() == PlayerId.PLAYER_ONE) {
			for (int piece : player1.getAvailablePieces()) {
				playerPieceList.add(piece);
			}
		} else {
			for (int piece : player2.getAvailablePieces()) {
				playerPieceList.add(piece);
			}
		}

		for (Move move : movesInRound) {
			Integer piece = move.getPiece();
			if (piece <= 0 || piece >= 10) {
				throw new GameEngineException("illegal piece: " + piece);
			}
			if (!playerPieceList.contains(piece)) {
				throw new GameEngineException("unavailable piece: " + piece);
			}
			board.placePiece(move.getX(), move.getY(), piece);
			playerPieceList.remove(piece);
		}

		Board.Statistics statisticsAfter = board.getStatistics();
		int bonus = (statisticsAfter.getOccupiedColumns() - statisticsBefore.getOccupiedColumns()) * 5
				+ (statisticsAfter.getOccupiedRows() - statisticsBefore.getOccupiedRows()) * 5
				+ (statisticsAfter.getOccupiedSquares() - statisticsBefore.getOccupiedSquares()) * 5;
		if (playerPieceList.isEmpty()) {
			bonus += 10;
		}

		int score;
		if (game.getCurrentPlayer() == PlayerId.PLAYER_ONE) {
			score = player1.getScore();
			score += movesInRound.length + bonus;
		} else {
			score = player2.getScore();
			score += movesInRound.length + bonus;
		}

		int[] availablePieces = game.getAvailablePieces();
		int index = 0;
		for (; playerPieceList.size() < 7 && index < availablePieces.length; index++) {
			playerPieceList.add(availablePieces[index]);
		}

		if (index > 0 && index < availablePieces.length) {
			availablePieces = new int[availablePieces.length - index];
			System.arraycopy(game.getAvailablePieces(), index, availablePieces, 0, availablePieces.length);
		} else if (index >= availablePieces.length) {
			availablePieces = new int[0];
		}
		game.setAvailablePieces(availablePieces);

		int[] playerPieces = new int[playerPieceList.size()];
		for (int i = 0; i < playerPieceList.size(); i++) {
			playerPieces[i] = playerPieceList.get(i);
		}

		List<Round> rounds = game.getRounds();
		if (rounds == null) {
			rounds = new ArrayList<>();
		}
		rounds.add(new Round(game.getCurrentPlayer(), movesInRound));

		PlayerId nextPlayerId;
		if (game.getCurrentPlayer() == PlayerId.PLAYER_ONE) {
			nextPlayerId = PlayerId.PLAYER_TWO;
			player1.setScore(score);
			player1.setAvailablePieces(playerPieces);
		} else {
			nextPlayerId = PlayerId.PLAYER_ONE;
			player2.setScore(score);
			player2.setAvailablePieces(playerPieces);
		}

		if (player1.getAvailablePieces().length == 0 && player2.getAvailablePieces().length == 0) {
			game.setCompleted(new Date());
			game.setCurrentPlayer(null);
		} else {
			game.setCurrentPlayer(nextPlayerId);
		}

		return game;
	}
}
