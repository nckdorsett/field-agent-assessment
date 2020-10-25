package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<SecurityClearance> actual = repository.findAll();
        assertTrue(actual.size() >= 2);
        assertNotNull(actual);
    }


    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        SecurityClearance topSecret = new SecurityClearance(2, "Top Secret");

        SecurityClearance actual = repository.findById(1);
        assertEquals(secret, actual);

        actual = repository.findById(2);
        assertEquals(topSecret, actual);

        actual = repository.findById(300000000);
        assertEquals(null, actual);
    }

    @Test
    void shouldFindByName() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");

        SecurityClearance actual = repository.findByName("Secret");
        assertEquals(secret, actual);

        actual = repository.findByName("Should Not Find");
        assertEquals(null, actual);
    }

    @Test
    void shouldAdd() {
        SecurityClearance confidential = new SecurityClearance(0, "Confidential");

        SecurityClearance actual = repository.add(confidential);
        List<SecurityClearance> all = repository.findAll();

        assertEquals(actual.getSecurityClearanceId(), all.size());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance confidential = new SecurityClearance(0, "Confidential");

        SecurityClearance actual = repository.add(confidential);

        SecurityClearance confidentialUpdate = new SecurityClearance(actual.getSecurityClearanceId(),"Update This thing");

        assertTrue(repository.update(confidentialUpdate));
    }

    @Test
    void shouldNotUpdateMissing() {
        SecurityClearance missing = new SecurityClearance(300000000, "This should not Exist");
        assertFalse(repository.update(missing));
    }

    @Test
    void shouldDelete() {
        SecurityClearance confidential = new SecurityClearance(0, "Confidential");

        SecurityClearance actual = repository.add(confidential);

        assertTrue(repository.delete(actual.getSecurityClearanceId()));
    }

    @Test
    void shouldNotDeleteMissing() {
        SecurityClearance missing = new SecurityClearance(300000000, "This should Not Exist");

        SecurityClearance actual = repository.add(missing);

        assertTrue(repository.delete(actual.getSecurityClearanceId()));
    }




}