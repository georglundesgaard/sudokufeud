package no.lundesgaard.sudokufeud.service;

public class IllegalGameStateException extends RuntimeException {
    private static final long serialVersionUID = 6178311417501124039L;

    public IllegalGameStateException(String message) {
        super(message);
    }
}
