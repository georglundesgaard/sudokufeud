package no.lundesgaard.sudokufeud.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import no.lundesgaard.sudokufeud.constants.Difficulty;

import org.junit.Test;

public class BoardTest {

	@Test
	public void initHasCorrectDimensions() throws Exception {
		// setup
		Integer c43 = 7;
		Integer c34 = null;

		// execution
		Board board = new Board(
				Difficulty.EASY,

				null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,

				null, null, null, null, c43, null, null, null, null,
				null, null, null, c34, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,

				null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null
		);

		// verify
		assertThat(board.getCell(3, 4).isOccupied()).describedAs("cell(3,4) occupied").isFalse();
		assertThat(board.getCell(4, 3).isOccupied()).describedAs("cell(4,3) occupied").isTrue();
	}

	@Test
	public void getAvailablePiecesWithBlankBoard() throws Exception {
		// setup
		Board board = blankBoard();

		// execution
		int[] availablePieces = board.getAvailablePieces();

		// verify
		assertThat(availablePieces).describedAs("available pieces").containsExactly(
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9,
				1, 2, 3, 4, 5, 6, 7, 8, 9
		);
	}

	private Board blankBoard() {
		return new Board(Difficulty.EASY);
	}

	@Test
	public void getAvailablePiecesWithFilledBoard() throws Exception {
		// setup
		Board board = filledBoard();

		// execution
		int[] availablePieces = board.getAvailablePieces();

		// verify
		assertThat(availablePieces).describedAs("available pieces").isEmpty();
	}

	@Test
	public void getAvailablePiecesWithPartiallyFilledBoard() throws Exception {
		// setup
		Board board = sampleBoard();

		// execution
		int[] availablePieces = board.getAvailablePieces();

		// verify
		assertThat(availablePieces).describedAs("available pieces").containsExactly(
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
	}

	@Test
	public void placePiece() throws Exception {
		// setup
		Board board = sampleBoard();

		// execution
		board.placePiece(8, 2, 6);

		// verify
		assertThat(board.getCell(8, 2).isOccupied()).isTrue();
		Board boardAfter = new Board(
				Difficulty.EASY,

				null, null, 7, null, 6, 3, null, 9, 5,
				null, null, 6, null, null, 9, null, 7, 2,
				1, null, null, null, 5, 4, 3, 8, 6,

				null, null, null, null, null, 2, null, null, null,
				6, null, 2, null, 3, 8, 5, null, 9,
				5, null, 4, 9, null, null, null, 3, 7,

				null, 9, 5, null, null, 1, null, 2, 8,
				null, null, null, null, 9, null, null, null, null,
				4, 3, null, null, 2, null, null, 5, 1
		);
		assertThat(board).isEqualTo(boardAfter);
	}

	@Test
	public void placePieceOnOccupiedCellThrowsException() throws Exception {
		// setup
		Board board = sampleBoard();

		// execute & verify
		try {
			board.placePiece(2, 0, 1);
		} catch (BoardException e) {
			assertThat(e.getMessage()).isEqualTo("cell(2,0) is occupied");
			return;
		}
		fail("expected BoardException");
	}

	private Board sampleBoard() {
		return new Board(
				Difficulty.EASY,

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
	public void getStatisticsWithBlankBoard() throws Exception {
		// setup
		Board board = blankBoard();

		// execution
		Board.Statistics statistics = board.getStatistics();

		// verify
		assertThat(statistics.getOccupiedColumns()).isEqualTo(0);
		assertThat(statistics.getOccupiedRows()).isEqualTo(0);
		assertThat(statistics.getOccupiedSquares()).isEqualTo(0);
	}

	@Test
	public void getStatisticsWithFilledBoard() throws Exception {
		// setup
		Board board = filledBoard();

		// execution
		Board.Statistics statistics = board.getStatistics();

		// verify
		assertThat(statistics.getOccupiedColumns()).isEqualTo(9);
		assertThat(statistics.getOccupiedRows()).isEqualTo(9);
		assertThat(statistics.getOccupiedSquares()).isEqualTo(9);
	}

	private Board filledBoard() {
		return new Board(
				Difficulty.EASY,

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
	}

	@Test
	public void getStatisticsWithPartiallyFilledBoard() throws Exception {
		// setup
		Board board = new Board(
				Difficulty.EASY,

				null, null, 7, 2, 6, 3, 1, 9, 5,
				3, null, 6, 1, 8, 9, 4, 7, 2,
				1, null, 9, 7, 5, 4, 3, 8, 6,

				9, null, null, 5, 7, 2, 8, null, 4,
				6, null, 2, 4, 3, 8, 5, null, 9,
				5, 8, 4, 9, 1, 6, 2, 3, 7,

				null, 9, 5, null, null, 1, null, 2, 8,
				null, null, null, null, 9, 5, null, 4, 3,
				4, 3, null, null, 2, 7, null, 5, 1
		);

		// execution
		Board.Statistics statistics = board.getStatistics();

		// verify
		assertThat(statistics.getOccupiedColumns()).describedAs("occupied columns").isEqualTo(2);
		assertThat(statistics.getOccupiedRows()).describedAs("occupied rows").isEqualTo(1);
		assertThat(statistics.getOccupiedSquares()).describedAs("occupied squares").isEqualTo(3);
	}
}
