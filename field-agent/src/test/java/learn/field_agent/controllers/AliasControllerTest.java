package learn.field_agent.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.field_agent.data.AliasJdbcTemplateRepository;
import learn.field_agent.models.Alias;
import learn.field_agent.models.SecurityClearance;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AliasControllerTest {

    @MockBean
    private AliasJdbcTemplateRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldAdd() throws Exception {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(0, "test alias 3", null, 3);
        Alias aliasOut = new Alias(3, "test alias 3", null, 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.add(any())).thenReturn(aliasOut);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(aliasIn);
        String jsonOut = jsonMapper.writeValueAsString(aliasOut);

        var request = post("/api/alias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonOut));
    }

    @Test
    void shouldReturn400WhenAddInvalid() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();
        Alias alias = new Alias();
        String jsonIn = jsonMapper.writeValueAsString(alias);

        var request = post("/api/alias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(2, "test alias 2", null, 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.update(any())).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(aliasIn);

        var request = put("/api/alias/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonIn));
    }

    @Test
    void shouldReturn409OnUpdateConflict() throws Exception {
        Alias aliasIn = new Alias(2, "test alias 2", null, 3);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(aliasIn);

        var request = put("/api/alias/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn404WhenUpdateIdNotFound() throws Exception {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(100, "test alias 2", "test", 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.update(any())).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(aliasIn);

        var request = put("/api/alias/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdateInvalid() throws Exception {
        Alias aliasIn = new Alias(2, null, "test", 3);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(aliasIn);

        var request = put("/api/alias/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.delete(2)).thenReturn(true);
        mvc.perform(delete("/api/delete/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeleteIdNotFound() throws Exception {
        when(repository.delete(100)).thenReturn(false);
        mvc.perform(delete("/api/delete/100"))
                .andExpect(status().isNotFound());
    }



}