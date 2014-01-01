package no.lundesgaard.sudokufeud.engine;

import no.lundesgaard.sudokufeud.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest {
    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";

    private GameEngine gameEngine;

    @Before
    public void setUp() throws Exception {
        this.gameEngine = new GameEngine();
    }

    @Test(expected = GameEngineException.class)
    public void createGameWithNullPlayer1ThrowsException() throws Exception {
        // setup
        Board board = emptyBoard();

        // execute
        gameEngine.createGame(null, PLAYER_2, board);
    }

    @Test(expected = GameEngineException.class)
    public void createGameWithEmptyPlayer1ThrowsException() throws Exception {
        // setup
        Board board = emptyBoard();

        // execute
        gameEngine.createGame("", PLAYER_2, board);
    }

    @Test(expected = GameEngineException.class)
    public void createGameWithNullPlayer2ThrowsException() throws Exception {
        // setup
        Board board = emptyBoard();

        // execute
        gameEngine.createGame(PLAYER_1, null, board);
    }

    @Test(expected = GameEngineException.class)
    public void createGameWithEmptyPlayer2ThrowsException() throws Exception {
        // setup
        Board board = emptyBoard();

        // execute
        gameEngine.createGame(PLAYER_1, "", board);
    }

    @Test(expected = GameEngineException.class)
    public void createGameWithNullBoardThrowsException() throws Exception {
        // setup

        // execute
        gameEngine.createGame(PLAYER_1, PLAYER_2, null);
    }

    private Board emptyBoard() {
        return new Board(Board.Difficulty.EASY);
    }

    @Test
    public void createGame() throws Exception {
        // setup
        Board board = emptyBoard();

        // execute
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, board);

        // verify
        assertThat(game.getId()).isNotNull();
        Player player1 = new Player(PLAYER_1, 0, null);
        assertThat(game.getPlayer1()).isEqualTo(player1);
        Player player2 = new Player(PLAYER_2, 0, null);
        assertThat(game.getPlayer2()).isEqualTo(player2);
        assertThat(game.getCurrentPlayer()).isNull();
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getAvailablePieces()).isEqualTo(board.getAvailablePieces());
        assertThat(game.getRounds()).isNull();
        assertThat(game.getStarted()).isNull();
        assertThat(game.getCompleted()).isNull();
        assertThat(game.getCreated()).isNotNull();
        assertThat(game.getModified()).isNull();
    }

    @Test(expected = GameEngineException.class)
    public void startGameWithUnknownPlayerThrowsException() throws Exception {
        // setup
        Board board = emptyBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, board);

        // execute
        gameEngine.startGame(game, "unknown");
    }

    @Test
    public void startGame() throws Exception {
        // setup
        Board board = emptyBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, board);

        // execute
        game = gameEngine.startGame(game, PLAYER_1);

        // verify
        assertThat(game.getId()).isEqualTo(game.getId());

        int[] availablePieces = new int[7];
        System.arraycopy(board.getAvailablePieces(), 0, availablePieces, 0, availablePieces.length);
        Player player1 = new Player(PLAYER_1, 0, availablePieces);
        assertThat(game.getPlayer1()).isEqualTo(player1);

        availablePieces = new int[7];
        System.arraycopy(board.getAvailablePieces(), 7, availablePieces, 0, availablePieces.length);
        Player player2 = new Player(PLAYER_2, 0, availablePieces);
        assertThat(game.getPlayer2()).isEqualTo(player2);

        assertThat(game.getCurrentPlayer()).isEqualTo(player1);
        assertThat(game.getBoard()).isEqualTo(board);

        availablePieces = new int[board.getAvailablePieces().length - 14];
        System.arraycopy(board.getAvailablePieces(), 14, availablePieces, 0, availablePieces.length);

        assertThat(game.getAvailablePieces()).isEqualTo(availablePieces);
        assertThat(game.getRounds()).isNull();
        assertThat(game.getStarted()).isNotNull();
        assertThat(game.getCompleted()).isNull();
        assertThat(game.getCreated()).isNotNull();
        assertThat(game.getModified()).isNotNull();
    }

    @Test
    public void executeRoundWithUnavailablePieceThrowsException() throws Exception {
        // setup
        Board initialBoard = sampleBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, initialBoard);
        game = gameEngine.startGame(game, PLAYER_1);

        // execute & verify
        try {
            gameEngine.executeRound(
                    game,
                    new Move(2, 2, 9)
            );
        } catch (GameEngineException e) {
            assertThat(e.getMessage()).isEqualTo("unavailable piece: 9");
            return;
        }
        fail("expected GameEngineException");
    }

    @Test
    public void executeRoundWithIllegalPiece1ThrowsException() throws Exception {
        // setup
        Board initialBoard = sampleBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, initialBoard);
        game = gameEngine.startGame(game, PLAYER_1);

        // execute & verify
        try {
            gameEngine.executeRound(
                    game,
                    new Move(2, 2, 0)
            );
        } catch (GameEngineException e) {
            assertThat(e.getMessage()).isEqualTo("illegal piece: 0");
            return;
        }
        fail("expected GameEngineException");
    }

    @Test
    public void executeRoundWithIllegalPiece2ThrowsException() throws Exception {
        // setup
        Board initialBoard = sampleBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, initialBoard);
        game = gameEngine.startGame(game, PLAYER_1);

        // execute & verify
        try {
            gameEngine.executeRound(
                    game,
                    new Move(2, 2, 10)
            );
        } catch (GameEngineException e) {
            assertThat(e.getMessage()).isEqualTo("illegal piece: 10");
            return;
        }
        fail("expected GameEngineException");
    }

    @Test
    public void executeRoundWithIllegalPiece3ThrowsException() throws Exception {
        // setup
        Board initialBoard = sampleBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, initialBoard);
        game = gameEngine.startGame(game, PLAYER_1);

        // execute & verify
        try {
            gameEngine.executeRound(
                    game,
                    new Move(2, 2, 11)
            );
        } catch (GameEngineException e) {
            assertThat(e.getMessage()).isEqualTo("illegal piece: 11");
            return;
        }
        fail("expected GameEngineException");
    }

    @Test
    public void executeRoundWithIllegalPiece4ThrowsException() throws Exception {
        // setup
        Board initialBoard = sampleBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, initialBoard);
        game = gameEngine.startGame(game, PLAYER_1);

        // execute & verify
        try {
            gameEngine.executeRound(
                    game,
                    new Move(2, 2, -1)
            );
        } catch (GameEngineException e) {
            assertThat(e.getMessage()).isEqualTo("illegal piece: -1");
            return;
        }
        fail("expected GameEngineException");
    }

    @Test
    public void executeRoundWithMoveOnOccupiedCellThrowsException() throws Exception {
        // setup
        Board initialBoard = sampleBoard();
        Game game = gameEngine.createGame(PLAYER_1, PLAYER_2, initialBoard);
        game = gameEngine.startGame(game, PLAYER_1);

        // execute & verify
        try {
            gameEngine.executeRound(
                    game,
                    new Move(2, 0, 1)
            );
        } catch (BoardException e) {
            assertThat(e.getMessage()).isEqualTo("cell(2,0) is occupied");
            return;
        }
        fail("expected BoardException");
    }

    private Board sampleBoard() {
        return new Board(
                Board.Difficulty.EASY,

                null, null, 7, null, 6, 3, null, 9, 5,
                null, null, 6, null, null, 9, null, 7, 2,
                1, null, null, null, 5, 4, 3, 8, null,

                null, null, null, null, null, 2, null, null, null,
                6, null, 2, null, 3, 8, 5, null, 9,
                5, null, 4, 9, null, null, null, 3, 7,

                null, 9, 5, null, null, 1, null, 2, 8,
                null, null, null, null, 9, null, null, null, null,
                4, 3, null, null, 2, null, null, 5, 1
        );
    }

    @Test
    public void sampleGame() throws Exception {
        Board initialBoard = sampleBoard();

        String playerId1 = PLAYER_1;
        String playerId2 = PLAYER_2;

        Game game = gameEngine.createGame(playerId1, playerId2, initialBoard);

        assertThat(game.getState()).isEqualTo(Game.State.NEW);
        assertThat(game.getBoard()).isEqualTo(initialBoard);
        assertThat(game.getPlayer1().getAvailablePieces()).describedAs("pieces playerId1").isNullOrEmpty();
        assertThat(game.getPlayer2().getAvailablePieces()).describedAs("pieces playerId2").isNullOrEmpty();
        assertThat(game.getAvailablePieces()).describedAs("available pieces").containsExactly(
                1, 2, 4, 8,
                1, 3, 4, 5, 8,
                2, 6, 7, 9,
                1, 3, 4, 5, 6, 7, 8, 9,
                1, 4, 7,
                1, 2, 6, 8,
                3, 4, 6, 7,
                1, 2, 3, 4, 5, 6, 7, 8,
                6, 7, 8, 9
        );

        game = gameEngine.startGame(game, playerId1);

        assertThat(game.getState()).isEqualTo(Game.State.RUNNING);
        assertThat(game.getBoard()).isEqualTo(initialBoard);
        assertThat(game.getPlayer1().getScore()).isEqualTo(0);
        assertThat(game.getPlayer2().getScore()).isEqualTo(0);
        assertThat(game.getCurrentPlayer().getPlayerId()).isEqualTo(playerId1);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").containsExactly(
                3, 4, 5, 6, 7, 8, 9,
                1, 4, 7,
                1, 2, 6, 8,
                3, 4, 6, 7,
                1, 2, 3, 4, 5, 6, 7, 8,
                6, 7, 8, 9
        );
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(1, 2, 4, 8, 1, 3, 4);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(5, 8, 2, 6, 7, 9, 1);

        // round 1: playerId1
        game = gameEngine.executeRound(
                game,
                new Move(6, 5, 2),
                new Move(0, 1, 3),
                new Move(4, 1, 8));

        Board board = new Board(
                Board.Difficulty.EASY,

                null, null, 7, null, 6, 3, null, 9, 5,
                3, null, 6, null, 8, 9, null, 7, 2,
                1, null, null, null, 5, 4, 3, 8, null,

                null, null, null, null, null, 2, null, null, null,
                6, null, 2, null, 3, 8, 5, null, 9,
                5, null, 4, 9, null, null, 2, 3, 7,

                null, 9, 5, null, null, 1, null, 2, 8,
                null, null, null, null, 9, null, null, null, null,
                4, 3, null, null, 2, null, null, 5, 1
        );

        assertThat(game.getState()).isEqualTo(Game.State.RUNNING);
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).isEqualTo(3);
        assertThat(game.getPlayer2().getScore()).isEqualTo(0);
        assertThat(game.getCurrentPlayer().getPlayerId()).isEqualTo(playerId2);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").containsExactly(
                6, 7, 8, 9,
                1, 4, 7,
                1, 2, 6, 8,
                3, 4, 6, 7,
                1, 2, 3, 4, 5, 6, 7, 8,
                6, 7, 8, 9
        );
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(1, 4, 1, 4, 3, 4, 5);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(5, 8, 2, 6, 7, 9, 1);

        // round 1: playerId2
        game = gameEngine.executeRound(
                game,
                new Move(3, 3, 5),
                new Move(5, 5, 6),
                new Move(3, 2, 7),
                new Move(3, 0, 2),
                new Move(3, 1, 1));

        board = new Board(
                Board.Difficulty.EASY,

                null, null, 7, 2, 6, 3, null, 9, 5,
                3, null, 6, 1, 8, 9, null, 7, 2,
                1, null, null, 7, 5, 4, 3, 8, null,

                null, null, null, 5, null, 2, null, null, null,
                6, null, 2, null, 3, 8, 5, null, 9,
                5, null, 4, 9, null, 6, 2, 3, 7,

                null, 9, 5, null, null, 1, null, 2, 8,
                null, null, null, null, 9, null, null, null, null,
                4, 3, null, null, 2, null, null, 5, 1
        );

        assertThat(game.getState()).isEqualTo(Game.State.RUNNING);
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(3);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(10);
        assertThat(game.getCurrentPlayer().getPlayerId()).describedAs("current player").isEqualTo(playerId1);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").containsExactly(
                4, 7,
                1, 2, 6, 8,
                3, 4, 6, 7,
                1, 2, 3, 4, 5, 6, 7, 8,
                6, 7, 8, 9
        );
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(1, 4, 1, 4, 3, 4, 5);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(8, 9, 6, 7, 8, 9, 1);

        // round 2: playerId1
        game = gameEngine.executeRound(
                game,
                new Move(6, 0, 1),
                new Move(8, 7, 3),
                new Move(6, 1, 4),
                new Move(7, 7, 4),
                new Move(8, 3, 4),
                new Move(5, 7, 5));
        board = new Board(
                Board.Difficulty.EASY,

                null, null, 7, 2, 6, 3, 1, 9, 5,
                3, null, 6, 1, 8, 9, 4, 7, 2,
                1, null, null, 7, 5, 4, 3, 8, null,

                null, null, null, 5, null, 2, null, null, 4,
                6, null, 2, null, 3, 8, 5, null, 9,
                5, null, 4, 9, null, 6, 2, 3, 7,

                null, 9, 5, null, null, 1, null, 2, 8,
                null, null, null, null, 9, 5, null, 4, 3,
                4, 3, null, null, 2, null, null, 5, 1
        );
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(9);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(10);
        assertThat(game.getCurrentPlayer().getPlayerId()).describedAs("current player").isEqualTo(playerId2);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").containsExactly(
                3, 4, 6, 7,
                1, 2, 3, 4, 5, 6, 7, 8,
                6, 7, 8, 9
        );
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(1, 4, 7, 1, 2, 6, 8);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(8, 9, 6, 7, 8, 9, 1);

        // round 2: playerId2
        game = gameEngine.executeRound(
                game,
                new Move(8, 2, 6),
                new Move(2, 2, 9),
                new Move(0, 3, 9),
                new Move(6, 3, 8),
                new Move(1, 5, 8),
                new Move(4, 5, 1),
                new Move(4, 3, 7));
        board = new Board(
                Board.Difficulty.EASY,

                null, null, 7, 2, 6, 3, 1, 9, 5,
                3, null, 6, 1, 8, 9, 4, 7, 2,
                1, null, 9, 7, 5, 4, 3, 8, 6,

                9, null, null, 5, 7, 2, 8, null, 4,
                6, null, 2, null, 3, 8, 5, null, 9,
                5, 8, 4, 9, 1, 6, 2, 3, 7,

                null, 9, 5, null, null, 1, null, 2, 8,
                null, null, null, null, 9, 5, null, 4, 3,
                4, 3, null, null, 2, null, null, 5, 1
        );
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(9);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(42);
        assertThat(game.getCurrentPlayer().getPlayerId()).describedAs("current player").isEqualTo(playerId1);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").containsExactly(
                4, 5, 6, 7, 8,
                6, 7, 8, 9
        );
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(1, 4, 7, 1, 2, 6, 8);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(3, 4, 6, 7, 1, 2, 3);

        // round 3: playerId1
        game = gameEngine.executeRound(
                game,
                new Move(1, 2, 2),
                new Move(3, 4, 4),
                new Move(7, 3, 6),
                new Move(7, 4, 1),
                new Move(5, 8, 7),
                new Move(0, 0, 8));
        board = new Board(
                Board.Difficulty.EASY,

                8, null, 7, 2, 6, 3, 1, 9, 5,
                3, null, 6, 1, 8, 9, 4, 7, 2,
                1, 2, 9, 7, 5, 4, 3, 8, 6,

                9, null, null, 5, 7, 2, 8, 6, 4,
                6, null, 2, 4, 3, 8, 5, 1, 9,
                5, 8, 4, 9, 1, 6, 2, 3, 7,

                null, 9, 5, null, null, 1, null, 2, 8,
                null, null, null, null, 9, 5, null, 4, 3,
                4, 3, null, null, 2, 7, null, 5, 1
        );
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(40);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(42);
        assertThat(game.getCurrentPlayer().getPlayerId()).describedAs("current player").isEqualTo(playerId2);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").containsExactly(
                7, 8, 9
        );
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(1, 4, 5, 6, 7, 8, 6);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(3, 4, 6, 7, 1, 2, 3);

        // round 3: playerId2
        game = gameEngine.executeRound(
                game,
                new Move(1, 4, 7),
                new Move(4, 6, 4),
                new Move(1, 7, 6),
                new Move(1, 3, 1),
                new Move(2, 3, 3),
                new Move(0, 7, 2),
                new Move(3, 6, 3));
        board = new Board(
                Board.Difficulty.EASY,

                8, null, 7, 2, 6, 3, 1, 9, 5,
                3, null, 6, 1, 8, 9, 4, 7, 2,
                1, 2, 9, 7, 5, 4, 3, 8, 6,

                9, 1, 3, 5, 7, 2, 8, 6, 4,
                6, 7, 2, 4, 3, 8, 5, 1, 9,
                5, 8, 4, 9, 1, 6, 2, 3, 7,

                null, 9, 5, 3, 4, 1, null, 2, 8,
                2, 6, null, null, 9, 5, null, 4, 3,
                4, 3, null, null, 2, 7, null, 5, 1
        );
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(40);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(79);
        assertThat(game.getCurrentPlayer().getPlayerId()).describedAs("current player").isEqualTo(playerId1);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").isEmpty();
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(1, 4, 5, 6, 7, 8, 6);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(7, 8, 9);

        // round 4: playerId1
        game = gameEngine.executeRound(
                game,
                new Move(1, 0, 4),
                new Move(1, 1, 5),
                new Move(2, 7, 1),
                new Move(0, 6, 7),
                new Move(2, 8, 8));
        board = new Board(
                Board.Difficulty.EASY,

                8, 4, 7, 2, 6, 3, 1, 9, 5,
                3, 5, 6, 1, 8, 9, 4, 7, 2,
                1, 2, 9, 7, 5, 4, 3, 8, 6,

                9, 1, 3, 5, 7, 2, 8, 6, 4,
                6, 7, 2, 4, 3, 8, 5, 1, 9,
                5, 8, 4, 9, 1, 6, 2, 3, 7,

                7, 9, 5, 3, 4, 1, null, 2, 8,
                2, 6, 1, null, 9, 5, null, 4, 3,
                4, 3, 8, null, 2, 7, null, 5, 1
        );
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(80);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(79);
        assertThat(game.getCurrentPlayer().getPlayerId()).describedAs("current player").isEqualTo(playerId2);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").isEmpty();
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(6, 6);
        assertThat(game.getPlayer2().getAvailablePieces()).containsExactly(7, 8, 9);

        // round 4: playerId2
        game = gameEngine.executeRound(
                game,
                new Move(6, 7, 7),
                new Move(3, 7, 8),
                new Move(6, 8, 9));
        board = new Board(
                Board.Difficulty.EASY,

                8, 4, 7, 2, 6, 3, 1, 9, 5,
                3, 5, 6, 1, 8, 9, 4, 7, 2,
                1, 2, 9, 7, 5, 4, 3, 8, 6,

                9, 1, 3, 5, 7, 2, 8, 6, 4,
                6, 7, 2, 4, 3, 8, 5, 1, 9,
                5, 8, 4, 9, 1, 6, 2, 3, 7,

                7, 9, 5, 3, 4, 1, null, 2, 8,
                2, 6, 1, 8, 9, 5, 7, 4, 3,
                4, 3, 8, null, 2, 7, 9, 5, 1
        );
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(80);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(97);
        assertThat(game.getCurrentPlayer().getPlayerId()).describedAs("current player").isEqualTo(playerId1);
        assertThat(game.getAvailablePieces()).describedAs("available pieces").isEmpty();
        assertThat(game.getPlayer1().getAvailablePieces()).containsExactly(6, 6);
        assertThat(game.getPlayer2().getAvailablePieces()).isEmpty();

        // round 5: playerId1
        game = gameEngine.executeRound(
                game,
                new Move(6, 6, 6),
                new Move(3, 8, 6));
        board = new Board(
                Board.Difficulty.EASY,

                8, 4, 7, 2, 6, 3, 1, 9, 5,
                3, 5, 6, 1, 8, 9, 4, 7, 2,
                1, 2, 9, 7, 5, 4, 3, 8, 6,

                9, 1, 3, 5, 7, 2, 8, 6, 4,
                6, 7, 2, 4, 3, 8, 5, 1, 9,
                5, 8, 4, 9, 1, 6, 2, 3, 7,

                7, 9, 5, 3, 4, 1, 6, 2, 8,
                2, 6, 1, 8, 9, 5, 7, 4, 3,
                4, 3, 8, 6, 2, 7, 9, 5, 1
        );
        assertThat(game.getBoard()).isEqualTo(board);
        assertThat(game.getPlayer1().getScore()).describedAs("score playerId1").isEqualTo(122);
        assertThat(game.getPlayer2().getScore()).describedAs("score playerId2").isEqualTo(97);
        assertThat(game.getCurrentPlayer()).describedAs("current player").isNull();
        assertThat(game.getAvailablePieces()).describedAs("available pieces").isEmpty();
        assertThat(game.getPlayer1().getAvailablePieces()).isEmpty();
        assertThat(game.getPlayer2().getAvailablePieces()).isEmpty();
        assertThat(game.getState()).isEqualTo(Game.State.COMPLETED);
        assertThat(game.getWinner().getPlayerId()).isEqualTo(playerId1);
        assertThat(game.getLoser().getPlayerId()).isEqualTo(playerId2);
    }
}
