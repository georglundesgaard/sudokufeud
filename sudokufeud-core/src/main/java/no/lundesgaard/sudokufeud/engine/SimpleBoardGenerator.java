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
						N, N, 7, N, 6, 3, N, 9, 5,
						N, N, 6, N, N, 9, N, 7, 2,
						1, N, N, N, 5, 4, 3, 8, N,

						N, N, N, N, N, 2, N, N, N,
						6, N, 2, N, 3, 8, 5, N, 9,
						5, N, 4, 9, N, N, N, 3, 7,

						N, 9, 5, N, N, 1, N, 2, 8,
						N, N, N, N, 9, N, N, N, N,
						4, 3, N, N, 2, N, N, 5, 1);

			case MEDIUM:
				return new Board(Difficulty.MEDIUM,
						9, 2, N, N, 5, N, 7, N, N,
						N, N, 5, N, N, 2, N, N, N,
						N, 1, 6, N, 7, N, 5, 3, N,

						N, N, N, N, N, N, 2, 5, N,
						N, 4, N, 5, N, 3, N, 9, N,
						N, 9, 2, N, N, N, N, N, N,

						N, 7, 1, N, 6, N, 3, 4, N,
						N, N, N, 1, N, N, 8, N, N,
						N, N, 9, N, 4, N, N, 2, 7);

			case HARD:
				return new Board(Difficulty.HARD,
						N, 6, 7, 1, N, N, N, N, 8,
						9, 2, N, N, N, N, N, 1, 4,
						N, N, 1, N, N, 7, N, N, N,

						4, N, N, N, N, N, 5, 6, N,
						N, N, N, 6, N, 8, N, N, N,
						N, 3, 9, N, N, N, N, N, 7,

						N, N, N, 5, N, N, 3, N, N,
						2, 9, N, N, N, N, N, 7, 5,
						5, N, N, N, N, 6, 9, 2, N);

			case EXPERT:
				return new Board(Difficulty.EXPERT,
						N, 6, N, N, 5, 7, N, N, 2,
						1, N, N, 9, N, N, N, N, N,
						9, N, N, N, N, N, N, 4, N,

						7, N, 2, N, 4, N, N, N, N,
						N, N, 8, N, 6, N, 2, N, N,
						N, N, N, N, 7, N, 9, N, 8,

						N, 1, N, N, N, N, N, N, 5,
						N, N, N, N, N, 5, N, N, 1,
						8, N, N, 6, 1, N, N, 2, N);

			default:
				throw new RuntimeException("Unknown difficulty: " + difficulty);
		}
	}
}
