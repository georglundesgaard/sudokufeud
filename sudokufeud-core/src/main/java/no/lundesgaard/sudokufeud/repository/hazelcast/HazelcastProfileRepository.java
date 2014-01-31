package no.lundesgaard.sudokufeud.repository.hazelcast;

import java.util.Map;
import java.util.Optional;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.ProfileNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class HazelcastProfileRepository extends AbstractHazelcastRepository<Profile> implements ProfileRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastProfileRepository.class);
	private static final String PROFILE_REPOSITORY_MAP_ID = "profileRepositoryMap";

	public HazelcastProfileRepository() {
		super(Profile.class, LOGGER, PROFILE_REPOSITORY_MAP_ID);
	}

	@Override
    public Profile findByUserId(String userId) {
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
}
