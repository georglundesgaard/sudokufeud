package no.lundesgaard.sudokufeud.service;

import no.lundesgaard.sudokufeud.model.Profile;

public interface ProfileService {
    Profile getProfile(String profileId);

    String getProfileIdByUserId(String userId);

    Profile getProfileByUserId(String userId);

    String createProfile(Profile profile);

    void updateProfile(String profileId, Profile profile);

    void deleteProfile(String profileId);
}
