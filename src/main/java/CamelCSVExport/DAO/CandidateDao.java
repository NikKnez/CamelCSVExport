package CamelCSVExport.DAO;

import CamelCSVExport.mapper.CandidateRowMapper;
import CamelCSVExport.model.Candidate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CandidateDao {
    private final JdbcTemplate jdbcTemplate;

    private List<Candidate> candidates;

    public CandidateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.candidates = findAll();
    }

    public List<Candidate> search(Candidate searchCriteria) {
        return candidates.stream()
                .filter(candidate ->
                        (searchCriteria.getJmbg() == null || candidate.getJmbg().equals(searchCriteria.getJmbg())) &&
                                (searchCriteria.getEmail() == null || candidate.getEmail().equalsIgnoreCase(searchCriteria.getEmail())) &&
                                (candidate.isEmployedAfterCompetition() == searchCriteria.isEmployedAfterCompetition())
                )
                .collect(Collectors.toList());
    }

    public List<Candidate> findAll() {
        String sql = "SELECT id, first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update FROM candidates";
        return jdbcTemplate.query(sql, new CandidateRowMapper());
    }

    public Candidate findById(Long id) {
        String sql = "SELECT id, first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update FROM candidates WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CandidateRowMapper());
    }

    public void save(Candidate candidate) {
        String sql = "INSERT INTO candidates (first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, candidate.getFirstName(), candidate.getLastName(), candidate.getJmbg(), candidate.getYearOfBirth(), candidate.getEmail(), candidate.getPhone(), candidate.getNotes(), candidate.getEmployedAfterCompetition(), new Date());
    }

    public void update(Candidate candidate) {
        String sql = "UPDATE candidates SET first_name = ?, last_name = ?, jmbg = ?, year_of_birth = ?, email = ?, phone = ?, notes = ?, employed_after_competition = ?, data_update = ? WHERE id = ?";
        jdbcTemplate.update(sql, candidate.getFirstName(), candidate.getLastName(), candidate.getJmbg(), candidate.getYearOfBirth(), candidate.getEmail(), candidate.getPhone(), candidate.getNotes(), candidate.getEmployedAfterCompetition(), new Date(), candidate.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM candidates WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Candidate findByJmbg(String jmbg) {
        String sql = "SELECT id, first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update FROM candidates WHERE jmbg = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{jmbg}, new CandidateRowMapper());
    }

    public List<Candidate> findByEmail(String email) {
        String sql = "SELECT id, first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update FROM candidates WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, new CandidateRowMapper());
    }

    public List<Candidate> findByEmployedAfterCompetition(boolean employedAfterCompetition) {
        String sql = "SELECT id, first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update FROM candidates WHERE employed_after_competition = ?";
        return jdbcTemplate.query(sql, new Object[]{employedAfterCompetition}, new CandidateRowMapper());
    }
}
