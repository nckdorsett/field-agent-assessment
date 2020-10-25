package learn.field_agent.domain;

import learn.field_agent.data.AliasJdbcTemplateRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {

    private AliasRepository repository;

    public AliasService(AliasJdbcTemplateRepository repository) {
        this.repository = repository;
    }

    public List<Alias> findAll() {
        return repository.findAll();
    }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("Alias ID must be 0 to while adding.", ResultType.INVALID);
            return result;
        }

        result.setPayload(repository.add(alias));
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("Alias ID must be set for update function.", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            result.addMessage("Alias ID not found.", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(alias);
        return result;
    }

    public boolean deleteById(int aliasId) {
        return repository.delete(aliasId);
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();
        if (alias == null) {
            result.addMessage("Alias must exist.", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getAliasName())) {
            result.addMessage("Alias name must be provided.", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getPersona())) {
            for (Alias a : findAll()) {
                if (a.getAliasName().equalsIgnoreCase(alias.getAliasName())
                    && a.getAliasId() != alias.getAliasId()) {
                    result.addMessage("Alias persona is required for a duplicate name.", ResultType.INVALID);
                    return result;
                }
            }
        }

        return result;
    }
}
