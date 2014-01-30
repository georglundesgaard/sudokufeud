package no.lundesgaard.sudokufeud.repository.hazelcast;

import no.lundesgaard.sudokufeud.model.Identifiable;
import no.lundesgaard.sudokufeud.repository.Repository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;

public abstract class AbstractHazelcastRepository<T extends Identifiable> implements Repository<T> {

    @Autowired
    private HazelcastInstance hazelcastInstance;
    
    private final Logger logger;
    private final String repositoryLockId;
    private final String repositoryMapId;

    public AbstractHazelcastRepository(Logger logger, String repositoryLockId, String repositoryMapId) {
        this.logger = logger;
        this.repositoryLockId = repositoryLockId;
        this.repositoryMapId = repositoryMapId;
    }
    
    protected HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }
    
    protected ILock getRepositoryLock() {
        return hazelcastInstance.getLock(repositoryLockId);
    } 
    
    protected IMap<String, T> getRepositoryMap() {
        return hazelcastInstance.getMap(repositoryMapId);
    }

    private LockedMap<String, T> getLockedMap() {
        return new LockedMap<>(logger, getRepositoryLock(), getRepositoryMap());
    }

    @Override
    public String create(T object) {
        String id = object.getId();

        try (LockedMap<String, T> lockedMap = getLockedMap()) {
            lockedMap.put(id, object);

            onCreated(object);
        }

        return id;
    }

    @Override
    public T read(String id) {
        IMap<String, T> objectMap = getRepositoryMap();
        if (id == null || !objectMap.containsKey(id)) {
            throw entityNotFoundException(id);
        }
        return objectMap.get(id);
    }

    @Override
    public void update(T newObject) {
        try (LockedMap<String, T> lockedMap = getLockedMap()) {
            T oldObject = read(newObject.getId());

            lockedMap.put(newObject.getId(), newObject);

            onUpdated(oldObject, newObject);
        }
    }

    @Override
    public void delete(String id) {
        IMap<String, T> objectMap = getRepositoryMap();
        if (id == null || !objectMap.containsKey(id)) {
            throw entityNotFoundException(id);
        }

        try (LockedMap<String, T> lockedMap = getLockedMap()) {
            T oldObject = lockedMap.remove(id);

            onDeleted(oldObject);
        }
    }

    protected abstract void onCreated(T newObject);

    protected abstract void onUpdated(T oldObject, T newObject);

    protected abstract void onDeleted(T oldObject);

    protected abstract EntityNotFoundException entityNotFoundException(String id);
}
