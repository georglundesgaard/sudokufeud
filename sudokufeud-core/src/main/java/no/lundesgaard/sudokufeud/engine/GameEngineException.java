package no.lundesgaard.sudokufeud.engine;

public class GameEngineException extends RuntimeException {
    private static final long serialVersionUID = 823300349294003419L;

    public GameEngineException(String message) {
        super(message);
    }
}
