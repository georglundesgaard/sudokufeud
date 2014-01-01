package no.lundesgaard.sudokufeud.repository.hazelcast;

import no.lundesgaard.sudokufeud.repository.hazelcast.exception.SuspectedDeadlockException;
import no.lundesgaard.sudokufeud.repository.hazelcast.exception.UnableToAquireLockException;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class LockedMap<K, V> implements AutoCloseable, Map<K, V> {
    private final Logger logger;
    private final Lock lock;
    private Map<K, V> map;

    LockedMap(Logger logger, Lock lock, Map<K, V> map) throws LockException {
        this.logger = logger;
        this.lock = lock;
        this.map = map;

        try {
            if (!lock.tryLock(30, TimeUnit.SECONDS)) {
                throw new UnableToAquireLockException(lock);
            }
        } catch (InterruptedException e) {
            throw new SuspectedDeadlockException(lock);
        }

        logger.trace("aquired lock: {}", lock);
    }

    @Override
    public void close() {
        this.map = Collections.unmodifiableMap(map);
        lock.unlock();
        logger.trace("released lock: {}", lock);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
