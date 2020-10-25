package learn.field_agent.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.field_agent.data.AgencyAgentJdbcTemplateRepository;
import learn.field_agent.data.SecurityClearanceJdbcTemplateRepository;
import learn.field_agent.models.Agency;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.Agent;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityClearanceControllerTest {

    @MockBean
    SecurityClearanceJdbcTemplateRepository securityClearanceRepository;

    @MockBean
    AgencyAgentJdbcTemplateRepository agencyAgentRepository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all);

        when(securityClearanceRepository.findAll()).thenReturn(all);

        mvc.perform(get("/api/security"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn404AllNotFound() throws Exception {
        List<SecurityClearance> all = List.of();

        when(securityClearanceRepository.findAll()).thenReturn(all);

        mvc.perform(get("/api/security"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetByName() throws Exception {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all.get(0));

        when(securityClearanceRepository.findByName(all.get(0).getName())).thenReturn(all.get(0));

        mvc.perform(get("/api/security/name/Secret"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn404NameNotFound() throws Exception {
        List<SecurityClearance> all = List.of();

        when(securityClearanceRepository.findByName("Secret")).thenReturn(null);

        mvc.perform(get("/api/security/name/Secret"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetById() throws Exception {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all.get(0));

        when(securityClearanceRepository.findById(all.get(0).getSecurityClearanceId())).thenReturn(all.get(0));

        mvc.perform(get("/api/security/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn404IdNotFound() throws Exception {
        List<SecurityClearance> all = List.of();

        when(securityClearanceRepository.findById(10)).thenReturn(null);

        mvc.perform(get("/api/security/id/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {
        SecurityClearance in = new SecurityClearance(0,"Confidential");
        SecurityClearance out = new SecurityClearance(1, "Confidential");

        when(securityClearanceRepository.add(any())).thenReturn(out);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(in);
        String expectedJson = jsonMapper.writeValueAsString(out);

        var request = post("/api/security")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn400WhenAddInvalid() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();
        SecurityClearance securityClearance = new SecurityClearance();
        String scJson = jsonMapper.writeValueAsString(securityClearance);

        var request = post("/api/security")
                .contentType(MediaType.APPLICATION_JSON)
                .content(scJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance in = new SecurityClearance(2, "New Top Secret");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(in)).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expected = jsonMapper.writeValueAsString(in);

        var request = put("/api/security/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected);

        mvc.perform(request)
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }

    @Test
    void shouldReturn409OnUpdateConflict() throws Exception {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance in = new SecurityClearance(2, "New Top Secret");

        when(securityClearanceRepository.findAll()).thenReturn(all);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expected = jsonMapper.writeValueAsString(in);

        var request = put("/api/security/3000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn404WhenUpdateNotFound() throws Exception {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance in = new SecurityClearance(20, "New Top Secret");

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(in)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expected = jsonMapper.writeValueAsString(in);

        var request = put("/api/security/20")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdateInvalid() throws Exception {
        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        SecurityClearance in = new SecurityClearance(2, null);

        when(securityClearanceRepository.findAll()).thenReturn(all);
        when(securityClearanceRepository.update(in)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expected = jsonMapper.writeValueAsString(in);

        var request = put("/api/security/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDelete() throws Exception {
        when(securityClearanceRepository.delete(2)).thenReturn(true);

        var request = delete("/api/security/2");

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {
        when(securityClearanceRepository.delete(2)).thenReturn(false);

        var request = delete("/api/security/2");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenDeleteInvalid() throws Exception {
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

        var request = delete("/api/security/1");

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }



}