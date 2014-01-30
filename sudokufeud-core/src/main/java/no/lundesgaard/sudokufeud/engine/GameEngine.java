package no.lundesgaard.sudokufeud.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import no.lundesgaard.sudokufeud.model.Board;
import no.lundesgaard.sudokufeud.model.Game;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.model.Player;
import no.lundesgaard.sudokufeud.model.Round;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class GameEngine {

    public Game startGame(Game game, String startingPlayerId) {
        if (game.getPlayer(startingPlayerId) == null) {
            throw new GameEngineException("unknown starting player: " + startingPlayerId);
        }

        Board board = game.getBoard();

        int[] availablePieces = new int[7];
        System.arraycopy(board.getAvailablePieces(), 0, availablePieces, 0, 7);
        Player player1 = new Player(game.getPlayer1().getPlayerId(), 0, availablePieces);

        availablePieces = new int[7];
        System.arraycopy(board.getAvailablePieces(), 7, availablePieces, 0, 7);
        Player player2 = new Player(game.getPlayer2().getPlayerId(), 0, availablePieces);

        availablePieces = new int[board.getAvailablePieces().length - 14];
        System.arraycopy(board.getAvailablePieces(), 14, availablePieces, 0, availablePieces.length);

        return new Game(
                game.getId(),
                player1,
                player2,
                startingPlayerId,
                board,
                availablePieces,
                null,
                DateTime.now(),
                null,
                game.getCreated(),
                DateTime.now());
    }

    public Game createGame(String playerId1, String playerId2, Board board) {
        if (playerId1 == null || playerId1.trim().length() == 0) {
            throw new GameEngineException("player1 required");
        }
        if (playerId2 == null || playerId2.trim().length() == 0) {
            throw new GameEngineException("player2 required");
        }
        if (board == null) {
            throw new GameEngineException("board required");
        }

        Player player1 = new Player(playerId1, 0, null);
        Player player2 = new Player(playerId2, 0, null);

        return new Game(
                Game.generateId(),
                player1,
                player2,
                null,
                board,
                board.getAvailablePieces(),
                null,
                null,
                null,
                DateTime.now(),
                null);
    }

    public Game executeRound(Game game, Move... movesInRound) {
        Board board = game.getBoard();
        Board.Statistics statisticsBefore = board.getStatistics();
        List<Integer> playerPieceList = new ArrayList<>();

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        if (player1.equals(game.getCurrentPlayer())) {
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
            board = board.placePiece(move.getX(), move.getY(), piece);
            playerPieceList.remove(piece);
        }

        Board.Statistics statisticsAfter = board.getStatistics();
        int bonus = (statisticsAfter.getOccupiedColumns() - statisticsBefore.getOccupiedColumns()) * 5
                + (statisticsAfter.getOccupiedRows() - statisticsBefore.getOccupiedRows()) * 5
                + (statisticsAfter.getOccupiedSquares() - statisticsBefore.getOccupiedSquares()) * 5;
        if (playerPieceList.isEmpty()) {
            bonus += 10;
        }

        int scorePlayer1 = player1.getScore();
        int scorePlayer2 = player2.getScore();
        if (player1.equals(game.getCurrentPlayer())) {
            scorePlayer1 += movesInRound.length + bonus;
        } else {
            scorePlayer2 += movesInRound.length + bonus;
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

        int[] playerPieces = new int[playerPieceList.size()];
        for (int i = 0; i < playerPieceList.size(); i++) {
            playerPieces[i] = playerPieceList.get(i);
        }

        Round[] rounds = game.getRounds();
        int roundIndex;
        if (rounds == null) {
            rounds = new Round[1];
            roundIndex = 0;
        } else {
            Round[] newRounds = new Round[rounds.length + 1];
            System.arraycopy(rounds, 0, newRounds, 0, rounds.length);
            rounds = newRounds;
            roundIndex = rounds.length - 1;
        }
        rounds[roundIndex] = new Round(game.getCurrentPlayer(), movesInRound);

        String nextPlayerId;
        if (player1.equals(game.getCurrentPlayer())) {
            nextPlayerId = player2.getPlayerId();
            player1 = new Player(player1.getPlayerId(), scorePlayer1, playerPieces);
        } else {
            nextPlayerId = player1.getPlayerId();
            player2 = new Player(player2.getPlayerId(), scorePlayer2, playerPieces);
        }

        DateTime completed = null;
        if (player1.getAvailablePieces().length == 0 && player2.getAvailablePieces().length == 0) {
            completed = DateTime.now();
            nextPlayerId = null;
        }

        return new Game(
                game.getId(),
                player1,
                player2,
                nextPlayerId,
                board,
                availablePieces,
                rounds,
                game.getStarted(),
                completed,
                game.getCreated(),
                DateTime.now());
    }
}
