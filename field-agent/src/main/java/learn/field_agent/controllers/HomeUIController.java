package learn.field_agent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeUIController {

    public HomeUIController() {

    }

    @GetMapping()
    public String displayHome() {
        return "home";
    }
}
