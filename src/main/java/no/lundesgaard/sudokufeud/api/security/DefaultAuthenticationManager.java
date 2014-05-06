package no.lundesgaard.sudokufeud.api.security;

import java.util.UUID;

import javax.annotation.PostConstruct;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationManager implements AuthenticationManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationManager.class);

	@Autowired
	private ProfileService profileService;

	@PostConstruct
	public void postConstruct() {
		Profile adminProfile = profileService.getProfileByUserId("admin");
		if (adminProfile == null) {
			String password = UUID.randomUUID().toString();
			Profile profile = new Profile();
			profile.setUserId("admin");
			profile.setPassword(password);
			profile.setName("SudokuFeud Administrator");
			profileService.createProfile(profile);
			LOGGER.info("\n\nSudokuFeud Adminstrator password: {}\n", password);
		}
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		validate(authentication);
		Profile profile = getOrCreateProfile(authentication);
		String password = getPassword(authentication);

		if (profile.validatePassword(password)) {
			if ("admin".equals(authentication.getPrincipal())) {
				return new DefaultAuthentication(authentication, true, "ROLE_ADMIN", "ROLE_USER");
			}
			return new DefaultAuthentication(authentication, true, "ROLE_USER");
		}

		authentication.setAuthenticated(false);
		return authentication;
	}

	private void validate(Authentication authentication) throws AuthenticationException{
		String userId = getUserId(authentication);
		if (userId == null || userId.trim().length() == 0) {
			throw new BadCredentialsException("missing user id");
		}
		String password = getPassword(authentication);
		if (password == null || password.trim().length() == 0) {
			throw new BadCredentialsException("blank password password not allowed");
		}
	}

	private Profile getOrCreateProfile(Authentication authentication) {
		String userId = getUserId(authentication);
		Profile profile = profileService.getProfileByUserId(userId);
		if (profile == null) {
			profile = new Profile();
			profile.setUserId(userId);
			profile.setPassword(getPassword(authentication));
			profile = profileService.createProfile(profile);
		}
		return profile;
	}

	private String getUserId(Authentication authentication) {
		return String.valueOf(authentication.getPrincipal());
	}

	private String getPassword(Authentication authentication) {
		return String.valueOf(authentication.getCredentials());
	}

}
