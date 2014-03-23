package no.lundesgaard.sudokufeud.api.controller;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DefaultController.DEFAULT_PATH)
public class DefaultController {

	public static final String DEFAULT_PATH = "/";

	@RequestMapping(method = RequestMethod.GET)
	public String getInfo(@AuthenticationPrincipal String userId) {
		return "SudokuFeud API 1.0 (%s)".replace("%s", userId);
	}
}
