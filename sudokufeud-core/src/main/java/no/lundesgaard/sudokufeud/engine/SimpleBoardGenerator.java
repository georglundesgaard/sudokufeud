package no.lundesgaard.sudokufeud.engine;

import no.lundesgaard.sudokufeud.model.Board;

public class SimpleBoardGenerator implements BoardGenerator {
    @Override
    public Board generateBoard(Board.Difficulty difficulty) {
        return new Board(difficulty,
                null, null, 7, null, 6, 3, null, 9, 5,
                null, null, 6, null, null, 9, null, 7, 2,
                1, null, null, null, 5, 4, 3, 8, null,

                null, null, null, null, null, 2, null, null, null,
                6, null, 2, null, 3, 8, 5, null, 9,
                5, null, 4, 9, null, null, null, 3, 7,

                null, 9, 5, null, null, 1, null, 2, 8,
                null, null, null, null, 9, null, null, null, null,
                4, 3, null, null, 2, null, null, 5, 1);
    }
}
