package learn.field_agent.controllers;

import learn.field_agent.domain.AgentService;
import learn.field_agent.domain.Result;
import learn.field_agent.models.Agent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/agent")
public class AgentUIController {

    private AgentService service;

    public AgentUIController(AgentService service) {
        this.service = service;
    }

    @GetMapping()
    public String displayAgents(Model model) {
        List<Agent> agents = service.findAll();
        model.addAttribute("agents", agents);
        return "AgentsDisplay";
    }

    @GetMapping("/add")
    public String displayAddForm(@ModelAttribute("agent") Agent agent, Model model) {
        List<Agent> agents = service.findAll();
        model.addAttribute("agents", agents);
        return "form";
    }

    @PostMapping("/add")
    public String handleAdd(@ModelAttribute("agent") @Valid Agent agent,
                            BindingResult result, Model model) {
        List<Agent> agents = service.findAll();
        model.addAttribute("agents", agents);
        if (result.hasErrors()) {
            return "form";
        }
        Result<Agent> agentResult = service.add(agent);
        return "redirect:/agent";
    }

    @GetMapping("/edit/{agentId}")
    public String displayEdit(@PathVariable int agentId, Model model) {
        Agent agent = service.findById(agentId);
        if (agent == null) {
            return "not-found";
        }
        List<Agent> agents = service.findAll();
        model.addAttribute("agents", agents);
        model.addAttribute("agent", agent);
        return "form";
    }

    @PostMapping("/edit/*")
    public String handleEdit(@ModelAttribute("agent") @Valid Agent agent,
                             BindingResult result, Model model) {
        List<Agent> agents = service.findAll();
        model.addAttribute("agents", agents);
        if (result.hasErrors()) {
            return "form";
        }
        Result<Agent> agentResult = service.update(agent);
        return "redirect:/agent";
    }

    @GetMapping("/delete/{agentId}")
    public String displayDelete(@PathVariable int agentId, Model model) {
        Agent agent = service.findById(agentId);
        if (agent == null) {
            return "not-found";
        }
        List<Agent> agents = service.findAll();
        model.addAttribute("agents", agents);
        model.addAttribute("agent", agent);
        return "delete";
    }

    @PostMapping("/delete/*")
    public String handleDelete(@ModelAttribute("agent") Agent agent, Model model) {
        List<Agent> agents = service.findAll();
        model.addAttribute("agents", agents);
        if(!service.deleteById(agent.getAgentId())) {
            return "not-found";
        }
        return "redirect:/agent";
    }
}
