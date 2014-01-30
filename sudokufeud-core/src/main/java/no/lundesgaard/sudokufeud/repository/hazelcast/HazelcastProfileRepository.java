package no.lundesgaard.sudokufeud.repository.hazelcast;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.repository.exception.EntityNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.ProfileNotFoundException;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;

@Repository
public class HazelcastProfileRepository
        extends AbstractHazelcastRepository<Profile>
        implements ProfileRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastProfileRepository.class);
    private static final String PROFILE_REPOSITORY_LOCK_ID = "profileRepositoryLock";
    private static final String PROFILE_REPOSITORY_MAP_ID = "profileRepositoryMap";

    private static final String USER_ID_PROFIL_ID_MAP_ID = "userIdProfileIdMap";
    
    public HazelcastProfileRepository() {
        super(LOGGER, PROFILE_REPOSITORY_LOCK_ID, PROFILE_REPOSITORY_MAP_ID);
    }
    
    private IMap<String, String> getUserIdProfileIdMap() {
        return getHazelcastInstance().getMap(USER_ID_PROFIL_ID_MAP_ID);
    }

    @Override
    public String readIdByUserId(String userId) {
        if (userId == null || !getUserIdProfileIdMap().containsKey(userId)) {
            throw new UnknownUserIdException(userId);
        }
        return getUserIdProfileIdMap().get(userId);
    }

    @Override
    public Profile readByUserId(String userId) {
        if (userId == null || !getUserIdProfileIdMap().containsKey(userId)) {
            throw new UnknownUserIdException(userId);
        }

        return read(getUserIdProfileIdMap().get(userId));
    }

    @Override
    protected void onCreated(Profile newProfile) {
        String userId = newProfile.getUserId();
        String profileId = newProfile.getId();
        getUserIdProfileIdMap().put(userId, profileId);
    }

    @Override
    protected void onUpdated(Profile oldProfile, Profile newProfile) {
        String oldUserId = oldProfile.getUserId();
        String newUserId = newProfile.getUserId();
        if (!oldUserId.equals(newUserId)) {
            getUserIdProfileIdMap().remove(oldUserId);
            getUserIdProfileIdMap().put(newUserId, newProfile.getId());
        }
    }

    @Override
    protected void onDeleted(Profile oldProfile) {
        getUserIdProfileIdMap().remove(oldProfile.getUserId());
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(String key) {
        return new ProfileNotFoundException(key);
    }
}
