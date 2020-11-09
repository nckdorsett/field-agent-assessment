package learn.field_agent.models;

import learn.field_agent.validation.NoAgentUnderTwelve;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoAgentUnderTwelve(message = "Agents must be older than 12.")
public class Agent {

    private int agentId;

    @NotBlank(message = "First Name is required.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last Name is required.")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Min(value = 36, message = "Height must be between 36 and 96 inches.")
    @Max(value = 96, message = "Height must be between 36 and 96 inches.")
    private int heightInInches;

    private List<AgentAgency> agencies = new ArrayList<>();
    private List<Alias> aliases = new ArrayList<>();

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }

    public List<AgentAgency> getAgencies() {
        return new ArrayList<>(agencies);
    }

    public void setAgencies(List<AgentAgency> agencies) {
        this.agencies = agencies;
    }

    public List<Alias> getAliases() {
        return new ArrayList<>(aliases);
    }

    public void setAliases(List<Alias> aliases) {
        this.aliases = aliases;
    }
}
