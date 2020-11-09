package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.models.Agent;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgentService {

    private final AgentRepository repository;

    public AgentService(AgentRepository repository) {
        this.repository = repository;
    }

    public List<Agent> findAll() {
        return repository.findAll();
    }

    public Agent findById(int agentId) {
        return repository.findById(agentId);
    }

    public Result<Agent> add(Agent agent) {
        Result<Agent> result = validate(agent);
        if (!result.isSuccess()) {
            return result;
        }

        if (agent.getAgentId() != 0) {
            result.addMessage("agentId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        agent = repository.add(agent);
        result.setPayload(agent);
        return result;
    }

    public Result<Agent> update(Agent agent) {
        Result<Agent> result = validate(agent);
        if (!result.isSuccess()) {
            return result;
        }

        if (agent.getAgentId() <= 0) {
            result.addMessage("agentId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(agent)) {
            String msg = String.format("agentId: %s, not found", agent.getAgentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int agentId) {
        return repository.deleteById(agentId);
    }

    private Result<Agent> validate(Agent agent) {
        Result<Agent> result = new Result<>();
        if (agent == null) {
            result.addMessage("agent cannot be null", ResultType.INVALID);
            return result;
        }
        return result;
    }
}
