package no.lundesgaard.sudokufeud.repository.exception;

import java.util.Formatter;

public class UnknownUserIdException extends RuntimeException {
    private static final long serialVersionUID = 900484411370882736L;

    private final String userId;

    public UnknownUserIdException(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getMessage() {
        return new Formatter().format("Unknown user id: %s", userId).toString();
    }
}
