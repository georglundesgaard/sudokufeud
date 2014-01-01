package no.lundesgaard.sudokufeud.service.standard;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.service.ProfileService;

public class StandardProfileService implements ProfileService {
    private ProfileRepository profileRepository;

    public StandardProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public String createProfile(Profile profile) {
        return profileRepository.create(profile);
    }

    @Override
    public Profile getProfile(String profileId) {
        return profileRepository.read(profileId);
    }

    @Override
    public String getProfileIdByUserId(String userId) {
        return profileRepository.readIdByUserId(userId);
    }

    @Override
    public Profile getProfileByUserId(String userId) {
        return profileRepository.readByUserId(userId);
    }

    @Override
    public void updateProfile(String profileId, Profile profile) {
        profileRepository.update(profile);
    }

    @Override
    public void deleteProfile(String profileId) {
        profileRepository.delete(profileId);
    }
}
