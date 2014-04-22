package no.lundesgaard.sudokufeud.service;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private ProfileRepository profileRepository;

	public Profile createProfile(Profile profile) {
		return profileRepository.save(profile);
	}

	public Profile getProfileByUserId(String userId) {
		return profileRepository.findByUserId(userId);
	}
}
