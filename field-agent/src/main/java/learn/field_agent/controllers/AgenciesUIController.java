package learn.field_agent.controllers;

import learn.field_agent.domain.AgencyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agencies")
public class AgenciesUIController {

    private AgencyService service;

    public AgenciesUIController(AgencyService service) {
        this.service = service;
    }

    @GetMapping()
    public String displayAgencies() {
        return "comingSoon";
    }
}
