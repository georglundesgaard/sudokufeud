package no.lundesgaard.sudokufeud.repository.hazelcast;

public abstract class AbstractHazelcastRepository {} /*<T extends Identifiable> implements Repository<T> {

	private final Class<T> objectType;
	private final Logger logger;
	private final String repositoryMapId;
	@Autowired
	protected HazelcastInstance hazelcastInstance;

	public AbstractHazelcastRepository(Class<T> objectType, Logger logger, String repositoryMapId) {
		this.objectType = objectType;
		this.logger = logger;
		this.repositoryMapId = repositoryMapId;
	}

	protected IMap<String, T> getRepositoryMap() {
		return hazelcastInstance.getMap(repositoryMapId);
	}

	@Override
	public T create(T object) {
		String id = object.getId();
		IMap<String, T> repositoryMap = getRepositoryMap();
		repositoryMap.put(id, object);
		logger.debug("{} created with id <{}>", objectType.getSimpleName(), id);
		return object;
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
	public T update(T updatedObject) {
		IMap<String, T> repositoryMap = getRepositoryMap();
		String id = updatedObject.getId();
		repositoryMap.put(id, updatedObject);
		logger.debug("{} with id <{}> updated", objectType.getSimpleName(), id);
		return updatedObject;
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
}*/
