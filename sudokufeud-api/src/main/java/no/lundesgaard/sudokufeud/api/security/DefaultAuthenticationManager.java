package no.lundesgaard.sudokufeud.api.security;

import static java.lang.String.valueOf;

import java.util.UUID;

import javax.annotation.PostConstruct;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
		try {
			profileService.getProfileByUserId("admin");
		} catch (UnknownUserIdException e) {
			String password = UUID.randomUUID().toString();
			Profile profile = new Profile("admin", password, "SudokuFeud Administrator");
			profileService.createProfile(profile);
			LOGGER.info("\n\nSudokuFeud Adminstrator password: {}\n", password);
		}
	}

	@Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
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

    private Profile getOrCreateProfile(Authentication authentication) {
        String userId = getUserId(authentication);
        try {
            return profileService.getProfileByUserId(userId);
        } catch (UnknownUserIdException e) {
            Profile profile = new Profile(userId, getPassword(authentication), null);
            profileService.createProfile(profile);
            return profile;
        }
    }

    private String getUserId(Authentication authentication) {
        return valueOf(authentication.getPrincipal());
    }

    private String getPassword(Authentication authentication) {
        return valueOf(authentication.getCredentials());
    }

}
