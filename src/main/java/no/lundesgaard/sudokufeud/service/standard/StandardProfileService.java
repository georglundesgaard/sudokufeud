package no.lundesgaard.sudokufeud.service.standard;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StandardProfileService implements ProfileService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile createProfile(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public Profile getProfile(Long profileId) {
		return profileRepository.findOne(profileId);
	}

//	@Override
//	public String getProfileIdByUserId(String userId) {
//		return profileRepository.findByUserId(userId).getId();
//	}

	@Override
	public Profile getProfileByUserId(String userId) {
		return profileRepository.findByUserId(userId);
	}

//	@Override
//	public void updateProfile(Profile updatedProfile) {
//		Profile profile = profileRepository.findOne(updatedProfile.getId());
//		profile.setUserId(updatedProfile.getUserId());
//		profile.setName(updatedProfile.getName());
//		profile.setPassword(updatedProfile.getPassword());
//	}

//	@Override
//	public void deleteProfile(String profileId) {
//		profileRepository.delete(profileId);
//	}
}
