package no.lundesgaard.sudokufeud.model;

public class BoardException extends RuntimeException {
    private static final long serialVersionUID = -3644866093606458544L;

    public BoardException(String message) {
        super(message);
    }
}
