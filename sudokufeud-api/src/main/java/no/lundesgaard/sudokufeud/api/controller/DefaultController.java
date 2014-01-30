package no.lundesgaard.sudokufeud.api.controller;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    @RequestMapping("/")
    @ResponseBody
    public String getInfo(@AuthenticationPrincipal String userId) {
        return "SudokuFeud API 1.0 (%s)".replace("%s", userId);
    }
}
