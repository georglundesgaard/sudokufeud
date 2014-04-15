package no.lundesgaard.sudokufeud.model;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import no.lundesgaard.sudokufeud.constants.Difficulty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Board implements Serializable {
	private static final long serialVersionUID = 5882709926255690461L;

	private static final int WIDTH = 9;
	private static final int HEIGHT = 9;
	private static final int SQUARE_SIZE = 9;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_ID_SEQ")
	@SequenceGenerator(name = "BOARD_ID_SEQ", sequenceName = "board_id_seq")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("y ASC, x ASC")
	private List<Cell> cells;

	public Board() {
	}

	public Board(Difficulty difficulty, Integer... pieces) {
		this.difficulty = difficulty;
		this.cells = new ArrayList<>();

		placePieces(pieces);
	}

	private void placePieces(Integer[] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			int x = i % 9;
			int y = i / 9;
			Cell cell = new Cell(x, y, pieces[i]);
			cell.setBoard(this);
			cells.add(cell);
		}
	}

	public Long getId() {
		return id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Cell getCell(int x, int y) {
		return cells.get(y * 9 + x);
	}

	public void placePiece(int x, int y, int piece) {
		for (Cell cell : cells) {
			if (cell.getX() == x && cell.getY() == y) {
				if (cell.isOccupied()) {
					throw new BoardException(format("cell(%d,%d) is occupied", x, y));
				}
				cell.setPiece(piece);
				return;
			}
		}
		throw new BoardException(format("Unknown cell (%d,%d)", x, y));
	}

	public Statistics getStatistics() {
		boolean[] columns = new boolean[WIDTH];
		boolean[] rows = new boolean[HEIGHT];
		boolean[] squares = new boolean[WIDTH * HEIGHT / SQUARE_SIZE];
		
		Statistics statistics = new Statistics();
		cells.forEach(cell -> {
			int x = cell.getX();
			int y = cell.getY();
			
			if (x == 0) {
				rows[y] = true;
			}
			if (y == 0) {
				columns[x] = true;
			}
			
			columns[x] &= cell.isOccupied();
			if (y == HEIGHT - 1 && columns[x]) {
				statistics.occupiedColumns++;
			}
			
			rows[y] &= cell.isOccupied();
			if (x == WIDTH - 1 && rows[y]) {
				statistics.occupiedRows++;
			}
			
			int s = x / 3 + (y / 3) * 3;
			if (x % 3 == 0 && y % 3 == 0) {
				squares[s] = true;
			}
			squares[s] &= cell.isOccupied();

			if (x % 3 == 2 && y % 3 == 2 && squares[s]) {
				statistics.occupiedSquares++;
			}
		});

		return statistics;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder()
				.append("Difficulty: ")
				.append(this.difficulty)
				.append('\n');
		
		cells.forEach(cell -> {
			int x = cell.getX();
			int y = cell.getY();
			Integer piece = cell.getPiece();

			if (x == 0 && y % 3 == 0) {
				output.append("+-------+-------+-------+\n");
			}
			if (x % 3 == 0) {
				output.append("| ");
			}
			if (piece == null) {
				output.append('_');
			} else {
				output.append(piece);
			}
			output.append(' ');
			if (x == WIDTH - 1) {
				output.append("|\n");
			}
		});
		output.append("+-------+-------+-------+\n");

		return output.toString();
	}

	public int[] getAvailablePieces() {
		Integer[][] availablePieces = {
				{1, 1, 1, 1, 1, 1, 1, 1, 1},
				{2, 2, 2, 2, 2, 2, 2, 2, 2},
				{3, 3, 3, 3, 3, 3, 3, 3, 3},
				{4, 4, 4, 4, 4, 4, 4, 4, 4},
				{5, 5, 5, 5, 5, 5, 5, 5, 5},
				{6, 6, 6, 6, 6, 6, 6, 6, 6},
				{7, 7, 7, 7, 7, 7, 7, 7, 7},
				{8, 8, 8, 8, 8, 8, 8, 8, 8},
				{9, 9, 9, 9, 9, 9, 9, 9, 9}
		};
		
		cells.forEach(cell -> {
			int y = cell.getY();
			Integer piece = cell.getPiece();
			for (int i = 0; i < WIDTH; i++) {
				if (availablePieces[i][y] != null && availablePieces[i][y].equals(piece)) {
					availablePieces[i][y] = null;
					break;
				}
			}
		});
//
		List<Integer> resultList = new ArrayList<>();
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (availablePieces[x][y] != null) {
					resultList.add(availablePieces[x][y]);
				}
			}
		}

		int[] result = new int[resultList.size()];
		for (int i = 0; i < resultList.size(); i++) {
			result[i] = resultList.get(i);
		}
		return result;
	}

	public Integer[] toIntegerArray() {
		return cells.stream().map(Cell::getPiece).toArray(Integer[]::new);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Board rhs = (Board) obj;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.difficulty, rhs.difficulty)
				.append(this.cells, rhs.cells)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(difficulty)
				.append(cells)
				.toHashCode();
	}

	public static class Statistics implements Serializable {
		private static final long serialVersionUID = 6407683221451491833L;

		private int occupiedColumns;
		private int occupiedRows;
		private int occupiedSquares;

		public int getOccupiedColumns() {
			return occupiedColumns;
		}

		public int getOccupiedRows() {
			return occupiedRows;
		}

		public int getOccupiedSquares() {
			return occupiedSquares;
		}
	}
}
