package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentJdbcTemplateRepository;
import learn.field_agent.data.SecurityClearanceJdbcTemplateRepository;
import learn.field_agent.models.Agency;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.Agent;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceServiceTest {

    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceJdbcTemplateRepository securityClearanceRepository;

    @MockBean
    AgencyAgentJdbcTemplateRepository agencyAgentRepository;

    @Test
    void shouldAdd() {
        SecurityClearance clearanceIn = new SecurityClearance(0, "Top Secret");
        SecurityClearance clearanceOut = new SecurityClearance(1, "Top Secret");

        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        Result<SecurityClearance> result = service.add(clearanceIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(clearanceOut, result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        SecurityClearance clearanceIn = null;
        SecurityClearance clearanceOut = null;

        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        Result<SecurityClearance> result = service.add(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullName() {
        SecurityClearance clearanceIn = new SecurityClearance(0, null);
        SecurityClearance clearanceOut = new SecurityClearance(0, null);

        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        Result<SecurityClearance> result = service.add(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddBlankName() {
        SecurityClearance clearanceIn = new SecurityClearance(0, "    ");
        SecurityClearance clearanceOut = new SecurityClearance(0, "    ");

        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        Result<SecurityClearance> result = service.add(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddIdNotZero() {
        SecurityClearance clearanceIn = new SecurityClearance(1000, "This is good");
        SecurityClearance clearanceOut = new SecurityClearance(1001, "This is good");

        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        Result<SecurityClearance> result = service.add(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

        clearanceIn = new SecurityClearance(-1, "This is good");
        clearanceOut = new SecurityClearance(0, "This is good");

        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        result = service.add(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddDuplicateName() {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance clearanceIn = new SecurityClearance(0, "Top Secret");
        SecurityClearance clearanceOut = new SecurityClearance(1, "Top Secret");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.add(clearanceIn)).thenReturn(clearanceOut);

        Result<SecurityClearance> result = service.add(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance clearanceIn = new SecurityClearance(2, "Confidential");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(clearanceIn)).thenReturn(true);

        Result<SecurityClearance> result = service.update(clearanceIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(clearanceIn, result.getPayload());
    }

    @Test
    void shouldNotUpdateNull() {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance clearanceIn = null;

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(clearanceIn)).thenReturn(true);

        Result<SecurityClearance> result = service.update(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullOrBlankName() {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance clearanceIn = new SecurityClearance(2, null);

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(clearanceIn)).thenReturn(true);

        Result<SecurityClearance> result = service.update(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

        clearanceIn = new SecurityClearance(2, "    ");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(clearanceIn)).thenReturn(true);

        result = service.update(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateIdLessThanOrEqualToZero() {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance clearanceIn = new SecurityClearance(-100, "This is good");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(clearanceIn)).thenReturn(true);

        Result<SecurityClearance> result = service.update(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

        clearanceIn = new SecurityClearance(0, "this is good");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(clearanceIn)).thenReturn(true);

        result = service.update(clearanceIn);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateMissingId() {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance clearanceIn = new SecurityClearance(30000, "This is good");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(clearanceIn)).thenReturn(false);

        Result<SecurityClearance> result = service.update(clearanceIn);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldDelete() {
        List<SecurityClearance> allSecurityClearances = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        Agent zero = new Agent();
        zero.setAgentId(1);
        zero.setFirstName("Dingo");
        zero.setLastName("Nevada");
        zero.setHeightInInches(72);

        Agency agency = new Agency();
        agency.setAgencyId(1);
        agency.setShortName("Longhorn");
        agency.setLongName("Steakhouse");

        AgencyAgent dingo = new AgencyAgent();
        dingo.setAgencyId(1);
        dingo.setAgent(zero);
        dingo.setIdentifier("DNevada");
        dingo.setSecurityClearance(allSecurityClearances.get(0));
        dingo.setActivationDate(LocalDate.now());
        dingo.setActive(true);

        List<AgencyAgent> allAgencyAgents = List.of(dingo);

        when(agencyAgentRepository.findAll()).thenReturn(allAgencyAgents);
        when(securityClearanceRepository.findAll()).thenReturn(allSecurityClearances);
        when(securityClearanceRepository.delete(2)).thenReturn(true);

        Result<SecurityClearance> result = service.deleteById(2);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteMissingId() {
        List<SecurityClearance> allSecurityClearances = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        Agent zero = new Agent();
        zero.setAgentId(1);
        zero.setFirstName("Dingo");
        zero.setLastName("Nevada");
        zero.setHeightInInches(72);

        Agency agency = new Agency();
        agency.setAgencyId(1);
        agency.setShortName("Longhorn");
        agency.setLongName("Steakhouse");

        AgencyAgent dingo = new AgencyAgent();
        dingo.setAgencyId(1);
        dingo.setAgent(zero);
        dingo.setIdentifier("DNevada");
        dingo.setSecurityClearance(allSecurityClearances.get(0));
        dingo.setActivationDate(LocalDate.now());
        dingo.setActive(true);

        List<AgencyAgent> allAgencyAgents = List.of(dingo);

        when(agencyAgentRepository.findAll()).thenReturn(allAgencyAgents);
        when(securityClearanceRepository.findAll()).thenReturn(allSecurityClearances);
        when(securityClearanceRepository.delete(100)).thenReturn(false);

        Result<SecurityClearance> result = service.deleteById(2);

        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldNotDeleteSecurityClearanceIdInUse() {
        List<SecurityClearance> allSecurityClearances = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        Agent zero = new Agent();
        zero.setAgentId(1);
        zero.setFirstName("Dingo");
        zero.setLastName("Nevada");
        zero.setHeightInInches(72);

        Agency agency = new Agency();
        agency.setAgencyId(1);
        agency.setShortName("Longhorn");
        agency.setLongName("Steakhouse");

        AgencyAgent dingo = new AgencyAgent();
        dingo.setAgencyId(1);
        dingo.setAgent(zero);
        dingo.setIdentifier("DNevada");
        dingo.setSecurityClearance(allSecurityClearances.get(0));
        dingo.setActivationDate(LocalDate.now());
        dingo.setActive(true);

        List<AgencyAgent> allAgencyAgents = List.of(dingo);

        when(agencyAgentRepository.findAll()).thenReturn(allAgencyAgents);
        when(securityClearanceRepository.findAll()).thenReturn(allSecurityClearances);
        when(securityClearanceRepository.delete(1)).thenReturn(true);

        Result<SecurityClearance> result = service.deleteById(1);

        assertEquals(ResultType.INVALID, result.getType());
    }


}