package no.lundesgaard.sudokufeud.service;

import java.util.Formatter;

public class UnknownGameException extends RuntimeException {
    private final String profileId;
    private final String gameId;

    public UnknownGameException(String gameId, String profileId) {
        this.gameId = gameId;
        this.profileId = profileId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getProfileId() {
        return profileId;
    }

    @Override
    public String getMessage() {
        return new Formatter().format("Unknown game. (profileId=%s; gameId=%s)", profileId, gameId).toString();
    }
}
