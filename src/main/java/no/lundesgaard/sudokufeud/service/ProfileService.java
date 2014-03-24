package no.lundesgaard.sudokufeud.service;

import no.lundesgaard.sudokufeud.model.Profile;

public interface ProfileService {
	Profile getProfile(Long id);
//	String getProfileIdByUserId(String userId);
	Profile getProfileByUserId(String userId);
	Profile createProfile(Profile profile);
//	void updateProfile(Profile profile);
//	void deleteProfile(String profileId);
}
