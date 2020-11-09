package learn.field_agent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/missions")
public class MissionsUIController {

    public MissionsUIController() {

    }

    @GetMapping()
    public String displayMissions() {
        return "comingSoon";
    }
}
