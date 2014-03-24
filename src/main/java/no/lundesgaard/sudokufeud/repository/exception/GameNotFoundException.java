package no.lundesgaard.sudokufeud.repository.exception;

import java.util.Formatter;

import no.lundesgaard.sudokufeud.model.Game;

public class GameNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = -3721886250858515894L;

	public final String userId;

	public GameNotFoundException(String id) {
		super(Game.class, id);
		this.userId = null;
	}

	public GameNotFoundException(String id, String userId) {
		super(Game.class, id);
		this.userId = userId;
	}

	@Override
	public String getMessage() {
		if (userId == null) {
			return super.getMessage();
		}
		return super.getMessage() + new Formatter().format(". User id: %s", userId);
	}
}
