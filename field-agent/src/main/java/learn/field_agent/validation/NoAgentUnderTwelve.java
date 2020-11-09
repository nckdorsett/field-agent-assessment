package learn.field_agent.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {AgentAgeValidator.class})
@Documented
public @interface NoAgentUnderTwelve {
    String message() default "{Agent must be over 12 years old.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};
}
