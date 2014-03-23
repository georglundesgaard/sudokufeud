package no.lundesgaard.sudokufeud.engine;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.model.Board;

public interface BoardGenerator {
	Board generateBoard(Difficulty difficulty);
}
