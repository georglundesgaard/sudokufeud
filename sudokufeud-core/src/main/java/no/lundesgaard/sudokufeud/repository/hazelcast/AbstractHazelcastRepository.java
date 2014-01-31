package no.lundesgaard.sudokufeud.repository.hazelcast;

import no.lundesgaard.sudokufeud.model.Identifiable;
import no.lundesgaard.sudokufeud.repository.Repository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@SuppressWarnings("SpringJavaAutowiringInspection")
public abstract class AbstractHazelcastRepository<T extends Identifiable> implements Repository<T> {

	@Autowired
	protected HazelcastInstance hazelcastInstance;

	private final Class<T> objectType;
	private final Logger logger;
	private final String repositoryMapId;

	public AbstractHazelcastRepository(Class<T> objectType, Logger logger, String repositoryMapId) {
		this.objectType = objectType;
		this.logger = logger;
		this.repositoryMapId = repositoryMapId;
	}

	protected IMap<String, T> getRepositoryMap() {
		return hazelcastInstance.getMap(repositoryMapId);
	}

	@Override
	public void create(T object) {
		String id = object.getId();
		IMap<String, T> repositoryMap = getRepositoryMap();
		repositoryMap.put(id, object);
		logger.debug("{} created with id <{}>", objectType.getSimpleName(), id);
	}

	@Override
	public T read(String id) {
		IMap<String, T> repositoryMap = getRepositoryMap();
		if (id == null || !repositoryMap.containsKey(id)) {
			throw entityNotFoundException(id);
		}
		return repositoryMap.get(id);
	}

	@Override
	public void update(T updatedObject) {
		IMap<String, T> repositoryMap = getRepositoryMap();
		String id = updatedObject.getId();
		repositoryMap.put(id, updatedObject);
		logger.debug("{} with id <{}> updated", objectType.getSimpleName(), id);
	}

	@Override
	public void delete(String id) {
		IMap<String, T> repositoryMap = getRepositoryMap();
		if (id == null || !repositoryMap.containsKey(id)) {
			throw entityNotFoundException(id);
		}
		repositoryMap.remove(id);
		logger.debug("{} with id <{}> removed", objectType.getSimpleName(), id);
	}

	protected abstract EntityNotFoundException entityNotFoundException(String id);
}
