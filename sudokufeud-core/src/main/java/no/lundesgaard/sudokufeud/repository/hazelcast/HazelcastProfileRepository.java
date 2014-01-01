package no.lundesgaard.sudokufeud.repository.hazelcast;

import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.ProfileNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HazelcastProfileRepository
        extends AbstractHazelcastRepository<Profile>
        implements ProfileRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastProfileRepository.class);

    private final IMap<String, String> userIdProfileMap;

    public HazelcastProfileRepository(ILock profileRepositoryLock, IMap<String, Profile> profileMap, IMap<String, String> userIdProfileMap) {
        super(LOGGER, profileRepositoryLock, profileMap);
        this.userIdProfileMap = userIdProfileMap;
    }

    @Override
    public String readIdByUserId(String userId) {
        if (userId == null || !userIdProfileMap.containsKey(userId)) {
            throw new UnknownUserIdException(userId);
        }
        return userIdProfileMap.get(userId);
    }

    @Override
    public Profile readByUserId(String userId) {
        if (userId == null || !userIdProfileMap.containsKey(userId)) {
            throw new UnknownUserIdException(userId);
        }

        return read(userIdProfileMap.get(userId));
    }

    @Override
    protected void onCreated(Profile newProfile) {
        String userId = newProfile.getUserId();
        String profileId = newProfile.getId();
        userIdProfileMap.put(userId, profileId);
    }

    @Override
    protected void onUpdated(Profile oldProfile, Profile newProfile) {
        String oldUserId = oldProfile.getUserId();
        String newUserId = newProfile.getUserId();
        if (!oldUserId.equals(newUserId)) {
            userIdProfileMap.remove(oldUserId);
            userIdProfileMap.put(newUserId, newProfile.getId());
        }
    }

    @Override
    protected void onDeleted(Profile oldProfile) {
        userIdProfileMap.remove(oldProfile.getUserId());
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(String key) {
        return new ProfileNotFoundException(key);
    }
}
