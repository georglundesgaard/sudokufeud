package no.lundesgaard.sudokufeud.api.security;

import static java.lang.String.valueOf;

import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.repository.exception.UnknownUserIdException;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationManager implements AuthenticationManager {
    
    @Autowired
    private ProfileService profileService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        Profile profile = getOrCreateProfile(authentication);
        String password = getPassword(authentication);
        
        if (profile.validatePassword(password)) {
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
