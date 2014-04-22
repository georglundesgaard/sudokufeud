package no.lundesgaard.sudokufeud.engine;

import no.lundesgaard.sudokufeud.constants.Difficulty;
import no.lundesgaard.sudokufeud.model.Board;

import org.springframework.stereotype.Service;

@Service
public class SimpleBoardGenerator implements BoardGenerator {
	public static final Integer N = null;

	@Override
	public Board generateBoard(Difficulty difficulty) {
		switch (difficulty) {
			case EASY:
				return new Board(Difficulty.EASY,
						-8, -4, +7, -2, +6, +3, -1, +9, +5,
						-3, -5, +6, -1, -8, +9, -4, +7, +2,
						+1, -2, -9, -7, +5, +4, +3, +8, -6,

						-9, -1, -3, -5, -7, +2, -8, -6, -4,
						+6, -7, +2, -4, +3, +8, +5, -1, +9,
						+5, -8, +4, +9, -1, -6, -2, +3, +7,

						-7, +9, +5, -3, -4, +1, -6, +2, +8,
						-2, -6, -1, -8, +9, -5, -7, -4, -3,
						+4, +3, -8, -6, +2, -7, -9, +5, +1);

			case MEDIUM:
				return new Board(Difficulty.MEDIUM,
						+9, +2, -8, -3, +5, -6, +7, -1, -4,
						-7, -3, +5, -4, -1, +2, -9, -8, -6,
						-4, +1, +6, -9, +7, -8, +5, +3, -2,

						-6, -8, -3, -7, -9, -4, +2, +5, -1,
						-1, +4, -7, +5, -2, +3, -6, +9, -8,
						-5, +9, +2, -6, -8, -1, -4, -7, -3,

						-8, +7, +1, -2, +6, -9, +3, +4, -5,
						-2, -5, -4, +1, -3, -7, +8, -6, -9,
						-3, -6, +9, -8, +4, -5, -1, +2, +7);

			case HARD:
				return new Board(Difficulty.HARD,
						-3, +6, +7, +1, -5, -4, -2, -9, +8,
						+9, +2, -5, -8, -6, -3, -7, +1, +4,
						-8, -4, +1, -9, -2, +7, -6, -5, -3,

						+4, -1, -8, -7, -3, -9, +5, +6, -2,
						-7, -5, -2, +6, -4, +8, -1, -3, -9,
						-6, +3, +9, -2, -1, -5, -8, -4, +7,

						-1, -7, -4, +5, -9, -2, +3, -8, -6,
						+2, +9, -6, -3, -8, -1, -4, +7, +5,
						+5, -8, -3, -4, -7, +6, +9, +2, -1);

			case EXPERT:
				return new Board(Difficulty.EXPERT,
						-4, +6, -3, -1, +5, +7, -8, -9, +2,
						+1, -2, -7, +9, -8, -4, -6, -5, -3,
						+9, -8, -5, -2, -3, -6, -1, +4, -7,

						+7, -3, +2, -8, +4, -9, -5, -1, -6,
						-5, -9, +8, -3, +6, -1, +2, -7, -4,
						-6, -4, -1, -5, +7, -2, +9, -3, +8,

						-3, +1, -9, -7, -2, -8, -4, -6, +5,
						-2, -7, -6, -4, -9, +5, -3, -8, +1,
						+8, -5, -4, +6, +1, -3, -7, +2, -9);

			default:
				throw new RuntimeException("Unknown difficulty: " + difficulty);
		}
	}
}
