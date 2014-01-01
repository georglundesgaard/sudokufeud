package no.lundesgaard.sudokufeud.repository.exception;

import no.lundesgaard.sudokufeud.model.Game;

import java.util.Formatter;

public class GameNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = -3721886250858515894L;

    public final String playerId;

    public GameNotFoundException(String id) {
        super(Game.class, id);
        this.playerId = null;
    }

    public GameNotFoundException(String id, String playerId) {
        super(Game.class, id);
        this.playerId = playerId;
    }

    @Override
    public String getMessage() {
        if (playerId == null) {
            return super.getMessage();
        }
        return super.getMessage() + new Formatter().format(". Player id: %s", playerId);
    }
}
