package no.lundesgaard.sudokufeud.repository.hazelcast.exception;

import no.lundesgaard.sudokufeud.repository.hazelcast.LockException;

import java.util.concurrent.locks.Lock;

public class SuspectedDeadlockException extends LockException {
    private static final long serialVersionUID = 3169452216315489740L;

    public SuspectedDeadlockException(Lock lock) {
        super("suspected deadlock for lock: %s", lock);
    }
}
