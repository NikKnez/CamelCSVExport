package CamelCSVExport.mapper;

import CamelCSVExport.model.Candidate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateRowMapper implements RowMapper<Candidate> {
    @Override
    public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Candidate candidate = new Candidate();
        candidate.setId(rs.getLong("id"));
        candidate.setFirstName(rs.getString("first_name"));
        candidate.setLastName(rs.getString("last_name"));
        candidate.setJmbg(rs.getString("jmbg"));
        candidate.setYearOfBirth(rs.getInt("year_of_birth"));
        candidate.setEmail(rs.getString("email"));
        candidate.setPhone(rs.getString("phone"));
        candidate.setNotes(rs.getString("notes"));
        candidate.setEmployedAfterCompetition(rs.getBoolean("employed_after_competition"));
        candidate.setDataUpdate(rs.getObject("data_update", java.time.LocalDate.class));
        return candidate;
    }
}

