package no.lundesgaard.sudokufeud.repository.hazelcast;

import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import no.lundesgaard.sudokufeud.model.Identifiable;
import no.lundesgaard.sudokufeud.repository.Repository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;
import org.slf4j.Logger;

public abstract class AbstractHazelcastRepository<T extends Identifiable> implements Repository<T> {
    private final Logger logger;
    private final ILock lock;
    private final IMap<String, T> objectMap;

    public AbstractHazelcastRepository(Logger logger, ILock lock, IMap<String, T> objectMap) {
        this.logger = logger;
        this.lock = lock;
        this.objectMap = objectMap;
    }

    @Override
    public String create(T object) {
        String id = object.getId();

        try (LockedMap<String, T> lockedMap = new LockedMap<>(logger, lock, objectMap)) {
            lockedMap.put(id, object);

            onCreated(object);
        }

        return id;
    }

    @Override
    public T read(String id) {
        if (id == null || !objectMap.containsKey(id)) {
            throw entityNotFoundException(id);
        }
        return objectMap.get(id);
    }

    @Override
    public void update(T newObject) {
        try (LockedMap<String, T> lockedMap = new LockedMap<>(logger, lock, objectMap)) {
            T oldObject = read(newObject.getId());

            lockedMap.put(newObject.getId(), newObject);

            onUpdated(oldObject, newObject);
        }
    }

    @Override
    public void delete(String id) {
        if (id == null || !objectMap.containsKey(id)) {
            throw entityNotFoundException(id);
        }

        try (LockedMap<String, T> lockedMap = new LockedMap<>(logger, lock, objectMap)) {
            T oldObject = lockedMap.remove(id);

            onDeleted(oldObject);
        }
    }

    protected abstract void onCreated(T newObject);

    protected abstract void onUpdated(T oldObject, T newObject);

    protected abstract void onDeleted(T oldObject);

    protected abstract EntityNotFoundException entityNotFoundException(String id);
}
