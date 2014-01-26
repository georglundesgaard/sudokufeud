package no.lundesgaard.sudokufeud.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Board implements Serializable {
    private static final long serialVersionUID = 5882709926255690461L;

    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;
    private static final int SQUARE_SIZE = 9;

    private final Difficulty difficulty;
    private final Integer[][] cells;
    private final Statistics statistics;

    public Board(Difficulty difficulty, Integer... pieces) {
        this.difficulty = difficulty;
        this.cells = new Integer[WIDTH][HEIGHT];

        placePieces(pieces);
        this.statistics = calculateStatistics();
    }

    private void placePieces(Integer[] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            int x = i % 9;
            int y = i / 9;
            cells[x][y] = pieces[i];
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Board placePiece(int x, int y, int piece) {
        Integer[] pieces = new Integer[WIDTH * HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                int index = i + j * WIDTH;
                if (x == i && y == j) {
                    if (cells[x][y] != null) {
                        throw new BoardException("cell(" + x + "," + y + ") is occupied");
                    }
                    pieces[index] = piece;
                } else {
                    pieces[index] = cells[i][j];
                }
            }
        }
        return new Board(this.difficulty, pieces);
    }

    public Statistics getStatistics() {
        return statistics;
    }

    private Statistics calculateStatistics() {
        int occupiedColumns = 0;
        int occupiedRows = 0;
        int occupiedSquares = 0;

        boolean[] columns = new boolean[WIDTH];
        boolean[] rows = new boolean[HEIGHT];
        boolean[] squares = new boolean[WIDTH * HEIGHT / SQUARE_SIZE];

        for (int x = 0; x < columns.length; x++) {
            columns[x] = true;
            for (int y = 0; y < rows.length; y++) {
                if (x == 0) {
                    rows[y] = true;
                }

                columns[x] &= isCellOccupied(x, y);
                rows[y] &= isCellOccupied(x, y);

                if (x + 1 == WIDTH && rows[y]) {
                    occupiedRows++;
                }

                int s = x / 3 + (y / 3) * 3;
                if (x % 3 == 0 && y % 3 == 0) {
                    squares[s] = true;
                }
                squares[s] &= isCellOccupied(x, y);

                if (x % 3 == 2 && y % 3 == 2 && squares[s]) {
                    occupiedSquares++;
                }
            }

            if (columns[x]) {
                occupiedColumns++;
            }
        }

        return new Statistics(occupiedColumns, occupiedRows, occupiedSquares);
    }

    public boolean isCellOccupied(int x, int y) {
        return cells[x][y] != null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != this.getClass()) return false;

        Board other = (Board) o;
        return new EqualsBuilder()
                .append(this.difficulty, other.difficulty)
                .append(this.cells, other.cells)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
                .append(this.difficulty)
                .append(this.cells)
                .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append("Difficulty: ")
                .append(this.difficulty)
                .append('\n');

        for (int y = 0; y < HEIGHT; y++) {
            if (y % 3 == 0) {
                output.append("+-------+-------+-------+\n");
            }

            for (int x = 0; x < WIDTH; x++) {
                if (x % 3 == 0) {
                    output.append("| ");
                }

                Integer cell = cells[x][y];
                if (cell == null) {
                    output.append('_');
                } else {
                    output.append(cell);
                }
                output.append(' ');
            }

            output.append("|\n");
        }
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

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Integer piece = cells[x][y];

                for (int i = 0; i < WIDTH; i++) {
                    if (availablePieces[i][y] != null && availablePieces[i][y].equals(piece)) {
                        availablePieces[i][y] = null;
                        break;
                    }
                }
            }
        }

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
        Integer[] pieces = new Integer[WIDTH * HEIGHT];
        for (int i = 0; i < pieces.length; i++) {
            int x = i % 9;
            int y = i / 9;
            pieces[i] = cells[x][y];
        }
        return pieces;
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD,
        EXPERT
    }

    public static class Statistics implements Serializable {
        private static final long serialVersionUID = 6407683221451491833L;

        private final int occupiedColumns;
        private final int occupiedRows;
        private final int occupiedSquares;

        public Statistics(int occupiedColumns, int occupiedRows, int occupiedSquares) {
            this.occupiedColumns = occupiedColumns;
            this.occupiedRows = occupiedRows;
            this.occupiedSquares = occupiedSquares;
        }

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
