package learn.field_agent.validation;

import learn.field_agent.models.Agent;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgentAgeValidator implements ConstraintValidator<NoAgentUnderTwelve, Agent> {

    @Override
    public void initialize(NoAgentUnderTwelve constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(Agent agent, ConstraintValidatorContext constraintValidatorContext) {
        if (agent == null) {
            return true;
        }
        if (agent.getDob() != null && agent.getDob().isAfter(LocalDate.now().minusYears(12))) {
            return false;
        }
        return true;
    }
}
