package no.lundesgaard.sudokufeud.service.standard;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardProfileService implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public void createProfile(Profile profile) {
		profileRepository.create(profile);
	}

	@Override
	public Profile getProfile(String profileId) {
		return profileRepository.read(profileId);
	}

	@Override
	public String getProfileIdByUserId(String userId) {
		return profileRepository.findByUserId(userId).getId();
	}

	@Override
	public Profile getProfileByUserId(String userId) {
		return profileRepository.findByUserId(userId);
	}

	@Override
	public void updateProfile(Profile profile) {
		profileRepository.update(profile);
	}

	@Override
	public void deleteProfile(String profileId) {
		profileRepository.delete(profileId);
	}
}
