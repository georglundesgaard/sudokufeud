package no.lundesgaard.sudokufeud.repository.hazelcast;

public class HazelcastProfileRepository {} /*extends AbstractHazelcastRepository<Profile> implements ProfileRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastProfileRepository.class);
	private static final String PROFILE_REPOSITORY_MAP_ID = "profileRepositoryMap";

	public HazelcastProfileRepository() {
		super(Profile.class, LOGGER, PROFILE_REPOSITORY_MAP_ID);
	}

	@Override
	public Profile findByUserId(String userId) throws UnknownUserIdException {
		Map<String, Profile> repositoryMap = getRepositoryMap();
		Optional<Profile> profile = repositoryMap
				.values()
				.parallelStream()
				.filter((p) -> p.getUserId().equals(userId))
				.findFirst();

		if (profile.isPresent()) {
			return profile.get();
		}

		throw new UnknownUserIdException(userId);
	}

	@Override
	protected EntityNotFoundException entityNotFoundException(String key) {
		return new ProfileNotFoundException(key);
	}
}*/
