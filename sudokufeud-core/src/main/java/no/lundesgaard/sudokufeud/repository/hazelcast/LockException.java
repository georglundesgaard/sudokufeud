package no.lundesgaard.sudokufeud.repository.hazelcast;

import java.util.Formatter;
import java.util.concurrent.locks.Lock;

public class LockException extends RuntimeException {
    private static final long serialVersionUID = -8129482622474964825L;

    private final Lock lock;

    public LockException(String message, Lock lock) {
        super(message);
        this.lock = lock;
    }

    public Lock getLock() {
        return lock;
    }

    @Override
    public String getMessage() {
        return new Formatter().format(super.getMessage(), lock).toString();
    }
}
