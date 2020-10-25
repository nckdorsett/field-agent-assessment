package learn.field_agent.domain;

import learn.field_agent.data.AliasJdbcTemplateRepository;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasServiceTest {

    @Autowired
    AliasService service;

    @MockBean
    AliasJdbcTemplateRepository repository;

    @Test
    void shouldAdd() {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(0, "test alias 3", null, 3);
        Alias aliasOut = new Alias(3, "test alias 3", null, 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.add(aliasIn)).thenReturn(aliasOut);

        Result<Alias> result = service.add(aliasIn);
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(aliasOut, result.getPayload());
    }

    @Test
    void shouldNotAddNullAlias() {
        Result<Alias> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullOrBlankName() {
        Alias aliasIn = new Alias(0, null, null, 3);
        Result<Alias> result = service.add(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

        aliasIn = new Alias(0, "   ", null, 3);
        result = service.add(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddDuplicateNameWithoutPersona() {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(0, "test alias 2", null, 3);
        Alias aliasOut = new Alias(3, "test alias 2", null, 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.add(aliasIn)).thenReturn(aliasOut);

        Result<Alias> result = service.add(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldAddDuplicateNameWithPersona() {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(0, "test alias 2", "this is fine", 3);
        Alias aliasOut = new Alias(3, "test alias 2", "this is fine", 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.add(aliasIn)).thenReturn(aliasOut);

        Result<Alias> result = service.add(aliasIn);
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(aliasOut, result.getPayload());
    }

    @Test
    void shouldNotAddIdNotZero() {
        Alias aliasIn = new Alias(100, "test alias 2", "this is fine", 3);
        Result<Alias> result = service.add(aliasIn);
        assertEquals(ResultType.INVALID , result.getType());
        assertNull(result.getPayload());

        aliasIn = new Alias(-100, "test alias 2", "this is fine", 3);
        result = service.add(aliasIn);
        assertEquals(ResultType.INVALID , result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(2, "test alias 2", null, 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.update(aliasIn)).thenReturn(true);

        Result<Alias> result = service.update(aliasIn);
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(aliasIn, result.getPayload());
    }

    @Test
    void shouldNotUpdateNullAlias() {
        Result<Alias> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullOrBlankName() {
        Alias aliasIn = new Alias(2, null, null, 3);
        Result<Alias> result = service.update(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

        aliasIn = new Alias(2, "   ", null, 3);
        result = service.update(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateIdLessThanOrEqualToZero() {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(0, "test alias 2", null, 3);

        when(repository.findAll()).thenReturn(allAlias);

        Result<Alias> result = service.update(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

        aliasIn = new Alias(-100, "test alias 2", null, 3);
        result = service.update(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateMissingId() {
        List<Alias> allAlias = List.of(
                new Alias(1, "test alias 1", null, 1),
                new Alias(2, "test alias 2", "this is a test", 2)
        );

        Alias aliasIn = new Alias(100, "test alias 2", "test persona", 3);

        when(repository.findAll()).thenReturn(allAlias);
        when(repository.update(aliasIn)).thenReturn(false);

        Result<Alias> result = service.update(aliasIn);
        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldDelete() {
        when(repository.delete(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDelete() {
        when(repository.delete(2)).thenReturn(false);
        assertFalse(service.deleteById(2));
    }


}
