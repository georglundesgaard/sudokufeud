package no.lundesgaard.sudokufeud.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
	
	@RequestMapping(method = RequestMethod.GET, value = "health")
	public String getHealth() {
		return "OK";
	}
}
