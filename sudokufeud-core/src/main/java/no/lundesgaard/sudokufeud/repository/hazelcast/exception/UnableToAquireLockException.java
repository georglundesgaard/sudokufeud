package no.lundesgaard.sudokufeud.repository.hazelcast.exception;

import no.lundesgaard.sudokufeud.repository.hazelcast.LockException;

import java.util.concurrent.locks.Lock;

public class UnableToAquireLockException extends LockException {
    private static final long serialVersionUID = 5998492234140278499L;

    public UnableToAquireLockException(Lock lock) {
        super("unable to aquire lock: %s", lock);
    }
}
