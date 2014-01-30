package no.lundesgaard.sudokufeud.api.controller;

import no.lundesgaard.sudokufeud.api.SudokuFeudApiConfiguration;
import no.lundesgaard.sudokufeud.api.model.JsonProfile;
import no.lundesgaard.sudokufeud.api.model.JsonUpdatedProfile;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(ProfileController.PROFILE_PATH)
public class ProfileController {
    
    public static final String PROFILE_PATH = SudokuFeudApiConfiguration.ROOT_PATH + "/profile";
    
    @Autowired
    private ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<JsonProfile> getProfile(@AuthenticationPrincipal String userId) {
        Profile profile = profileService.getProfileByUserId(userId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLastModified(profile.getLastModified().getMillis());
        
        return new ResponseEntity<>(toJsonProfile(profile), httpHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<JsonProfile> updateProfile(
            @AuthenticationPrincipal String userId, 
            @RequestBody JsonUpdatedProfile jsonUpdatedProfile) {
        
        Profile profile = profileService.getProfileByUserId(userId);
        profile = toUpdatedProfile(jsonUpdatedProfile, profile);
        profileService.updateProfile(profile);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLastModified(profile.getLastModified().getMillis());
        
        return new ResponseEntity<>(toJsonProfile(profile), httpHeaders, HttpStatus.OK);
    }

    private JsonProfile toJsonProfile(Profile profile) {
        JsonProfile jsonProfile = new JsonProfile();
        jsonProfile.setUserId(profile.getUserId());
        jsonProfile.setName(profile.getName());
        return jsonProfile;
    }

    private Profile toUpdatedProfile(JsonUpdatedProfile jsonUpdatedProfile, Profile profile) {
        return new Profile(profile, 
                jsonUpdatedProfile.getUserId(),
                jsonUpdatedProfile.getPassword(),
                jsonUpdatedProfile.getName());
    }
}
