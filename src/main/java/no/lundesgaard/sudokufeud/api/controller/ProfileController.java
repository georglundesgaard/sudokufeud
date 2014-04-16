package no.lundesgaard.sudokufeud.api.controller;

import no.lundesgaard.sudokufeud.SudokuFeudConfiguration;
import no.lundesgaard.sudokufeud.api.model.JsonError;
import no.lundesgaard.sudokufeud.api.model.JsonProfile;
import no.lundesgaard.sudokufeud.api.model.JsonUpdatedProfile;
import no.lundesgaard.sudokufeud.model.Profile;
import no.lundesgaard.sudokufeud.service.ProfileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(ProfileController.PROFILE_PATH)
public class ProfileController {
	public static final String PROFILE_PATH = SudokuFeudConfiguration.ROOT_PATH + "/profile";
	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
	@Autowired
	private ProfileService profileService;

	@RequestMapping(method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<JsonProfile> getProfile(@AuthenticationPrincipal String userId) {
		Profile profile = profileService.getProfileByUserId(userId);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLastModified(profile.getLastModified().getTime());

		return new ResponseEntity<>(toJsonProfile(profile), httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<JsonProfile> updateProfile(
			@AuthenticationPrincipal String userId,
			@RequestBody JsonUpdatedProfile jsonUpdatedProfile) {

		Profile profile = profileService.getProfileByUserId(userId);
		String updatedUserId = jsonUpdatedProfile.getUserId();
		if (updatedUserId != null && updatedUserId.trim().length() > 0) {
			profile.setUserId(updatedUserId);
		}
		String updatedName = jsonUpdatedProfile.getName();
		if (updatedName != null && updatedName.trim().length() > 0) {
			profile.setName(updatedName);
		}
		String updatedPassword = jsonUpdatedProfile.getPassword();
		if (updatedPassword != null && updatedPassword.trim().length() > 0) {
			profile.setPassword(updatedPassword);
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLastModified(profile.getLastModified().getTime());

		return new ResponseEntity<>(toJsonProfile(profile), httpHeaders, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonError handleException(Exception e) {
		return jsonError(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private JsonError jsonError(Exception e, HttpStatus status) {
		LOGGER.debug("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
		return new JsonError(status, e.getMessage());
	}

	private JsonProfile toJsonProfile(Profile profile) {
		JsonProfile jsonProfile = new JsonProfile();
		jsonProfile.setUserId(profile.getUserId());
		jsonProfile.setName(profile.getName());
		return jsonProfile;
	}
}
