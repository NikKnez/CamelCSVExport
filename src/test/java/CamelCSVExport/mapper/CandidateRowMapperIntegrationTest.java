package CamelCSVExport.mapper;

import CamelCSVExport.CamelCsvExportApplication;
import CamelCSVExport.model.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CamelCsvExportApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CandidateRowMapperIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM candidates");
        String sqlInsert = "INSERT INTO candidates (id, first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlInsert, 1L, "Nik", "Knez", "1234567890123", 1986, "nik.knez@example.com", "1234567", "Test notes", true, LocalDate.of(2024, 1, 1));
    }

    @Test
    void testIntegrationWithDatabase() {
        String sqlSelect = "SELECT * FROM candidates WHERE id = ?";
        List<Candidate> candidates = jdbcTemplate.query(sqlSelect, new CandidateRowMapper(), 1L);

        assertNotNull(candidates);
        assertEquals(1, candidates.size());

        Candidate candidate = candidates.get(0);

        assertNotNull(candidate);
        assertEquals("Nik", candidate.getFirstName());
        assertEquals("Knez", candidate.getLastName());
        assertEquals("1234567890123", candidate.getJmbg());
        assertEquals(1986, candidate.getYearOfBirth());
        assertEquals("nik.knez@example.com", candidate.getEmail());
        assertEquals("1234567", candidate.getPhone());
        assertEquals("Test notes", candidate.getNotes());
        assertEquals(true, candidate.isEmployedAfterCompetition());
        assertEquals(LocalDate.of(2024, 1, 1), candidate.getDataUpdate());
    }
}
