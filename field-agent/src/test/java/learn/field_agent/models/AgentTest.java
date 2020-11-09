package learn.field_agent.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AgentTest {

    @Test
    void emptyTicketShouldFailValidation() {
        Agent agent = new Agent();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        assertEquals(4, violations.size());
    }

    @Test
    void blankFirstNameShouldFailValidation() {
        Agent agent = makeAgent();
        agent.setFirstName("  ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        assertEquals(1, violations.size());
    }

    @Test
    void nullFirstNameShouldFailValidation() {
        Agent agent = makeAgent();
        agent.setFirstName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        assertEquals(1, violations.size());
    }

    @Test
    void blankLastNameShouldFailValidation() {
        Agent agent = makeAgent();
        agent.setLastName("  ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        assertEquals(1, violations.size());
    }

    @Test
    void nullLastNameShouldFailValidation() {
        Agent agent = makeAgent();
        agent.setLastName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        assertEquals(1, violations.size());
    }

    @Test
    void invalidHeightShouldFailValidation() {
        Agent agent = makeAgent();
        agent.setHeightInInches(1);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        assertEquals(1, violations.size());

        agent.setHeightInInches(110);
        violations = validator.validate(agent);

        assertEquals(1, violations.size());
    }

    @Test
    void invalidDobShouldFailValidation() {
        Agent agent = makeAgent();
        agent.setDob(LocalDate.now().minusYears(11));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        assertEquals(1, violations.size());
    }

    Agent makeAgent() {
        //('Hazel','C','Sauven','1954-09-16',76),
        Agent agent = new Agent();
        agent.setAgentId(1);
        agent.setFirstName("Hazel");
        agent.setMiddleName("C");
        agent.setLastName("Sauven");
        agent.setDob(LocalDate.of(1954, 9, 16));
        agent.setHeightInInches(76);
        return agent;
    }
}