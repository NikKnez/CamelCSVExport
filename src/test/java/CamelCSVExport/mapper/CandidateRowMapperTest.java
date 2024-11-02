package CamelCSVExport.mapper;

import CamelCSVExport.model.Candidate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CandidateRowMapperTest {

    private final CandidateRowMapper candidateRowMapper = new CandidateRowMapper();

    @Test
    void testMapRow() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getString("first_name")).thenReturn("Nik");
        when(rs.getString("last_name")).thenReturn("Knez");
        when(rs.getString("jmbg")).thenReturn("1234567890123");
        when(rs.getInt("year_of_birth")).thenReturn(1986);
        when(rs.getString("email")).thenReturn("nik.knez@example.com");
        when(rs.getString("phone")).thenReturn("1234567");
        when(rs.getString("notes")).thenReturn("Some notes");
        when(rs.getBoolean("employed_after_competition")).thenReturn(true);
        when(rs.getObject("data_update", LocalDate.class)).thenReturn(LocalDate.of(2024, 1, 1));

        Candidate candidate = candidateRowMapper.mapRow(rs, 0);

        assertEquals(1L, candidate.getId());
        assertEquals("Nik", candidate.getFirstName());
        assertEquals("Knez", candidate.getLastName());
        assertEquals("1234567890123", candidate.getJmbg());
        assertEquals(1986, candidate.getYearOfBirth());
        assertEquals("nik.knez@example.com", candidate.getEmail());
        assertEquals("1234567", candidate.getPhone());
        assertEquals("Some notes", candidate.getNotes());
        assertEquals(true, candidate.isEmployedAfterCompetition());
        assertEquals(LocalDate.of(2024, 1, 1), candidate.getDataUpdate());
    }
}
