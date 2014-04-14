package no.lundesgaard.sudokufeud.repository.exception;

import java.util.Formatter;

import no.lundesgaard.sudokufeud.model.Game;

public class GameNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3721886250858515894L;

	public final long id;
	public final String userId;

	public GameNotFoundException(long id) {
		this.id = id;
		this.userId = null;
	}

	public GameNotFoundException(long id, String userId) {
		this.id = id;
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
