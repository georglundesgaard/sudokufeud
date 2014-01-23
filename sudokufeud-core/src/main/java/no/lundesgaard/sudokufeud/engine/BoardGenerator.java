package no.lundesgaard.sudokufeud.engine;

import no.lundesgaard.sudokufeud.model.Board;

public interface BoardGenerator {
    Board generateBoard(Board.Difficulty difficulty);
}
