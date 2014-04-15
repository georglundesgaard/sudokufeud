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
	public Profile getProfileByUserId(String userId) {
		return profileRepository.findByUserId(userId);
	}
}
