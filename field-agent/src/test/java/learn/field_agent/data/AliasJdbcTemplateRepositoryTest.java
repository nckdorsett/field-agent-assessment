package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasJdbcTemplateRepositoryTest {

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Alias> all = repository.findAll();

        assertNotNull(all);
        assertTrue(all.size() >= 3);
    }

    @Test
    void shouldAdd() {
        Alias alias = new Alias(0, "this", "new alias", 1);

        Alias actual = repository.add(alias);
        assertNotNull(actual);

        List<Alias> all = repository.findAll();
        assertEquals(actual.getAliasId(), all.size());
    }

    @Test
    void shouldUpdate() {
        Alias alias = new Alias(0, "this", "new alias", 1);

        alias = repository.add(alias);
        assertNotNull(alias);

        Alias aliasUpdated = new Alias(alias.getAliasId(), "update", "this alias", 1);
        assertTrue(repository.update(aliasUpdated));
    }

    @Test
    void shouldNotUpdateMissing() {
        Alias missingAlias = new Alias(repository.findAll().size()+1000, "missing", null, 1);
        assertFalse(repository.update(missingAlias));
    }

    @Test
    void shouldDelete() {
        Alias deleteThis = new Alias(0, "delete", "test", 1);
        deleteThis = repository.add(deleteThis);

        assertTrue(repository.delete(deleteThis.getAliasId()));
    }

    @Test
    void shouldNotDeleteMissing() {
        Alias missingAlias = new Alias(repository.findAll().size()+1000, "missing", null, 1);
        assertFalse(repository.delete(missingAlias.getAliasId()));
    }

}