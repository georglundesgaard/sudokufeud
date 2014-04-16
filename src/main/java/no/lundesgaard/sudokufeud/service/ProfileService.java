package no.lundesgaard.sudokufeud.service;

import no.lundesgaard.sudokufeud.model.Profile;

public interface ProfileService {
	Profile getProfileByUserId(String userId);
	Profile createProfile(Profile profile);
}
