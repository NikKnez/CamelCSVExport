package CamelCSVExport.DAO;

import CamelCSVExport.mapper.CandidateRowMapper;
import CamelCSVExport.model.Candidate;
import CamelCSVExport.model.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CandidateDao {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CandidateDao.class);


    private List<Candidate> candidates;

    public CandidateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.candidates = findAll();
    }


    public List<Candidate> search(SearchCriteria searchCriteria) {
        StringBuilder sql = new StringBuilder("SELECT id, first_name, last_name, jmbg, year_of_birth, email, phone, notes, employed_after_competition, data_update FROM candidates WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (searchCriteria.getJmbg() != null && !searchCriteria.getJmbg().isEmpty()) {
            sql.append(" AND jmbg = ?");
            params.add(searchCriteria.getJmbg());
        }

        if (searchCriteria.getEmail() != null && !searchCriteria.getEmail().isEmpty()) {
            sql.append(" AND email = ?");
            params.add(searchCriteria.getEmail());
        }

        if (searchCriteria.getEmployedAfterCompetition() != null) {
            sql.append(" AND employed_after_competition = ?");
            params.add(searchCriteria.getEmployedAfterCompetition());
        }

//        logger.info("Executing SQL: {} with parameters: {}", sql, params);
//        List<Candidate> results = jdbcTemplate.query(sql.toString(), params.toArray(), new CandidateRowMapper());
//        logger.info("Search Results: {}", results);
//        return results;

        return jdbcTemplate.query(sql.toString(), params.toArray(), new CandidateRowMapper());
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
        try {
            jdbcTemplate.update(sql, candidate.getFirstName(), candidate.getLastName(), candidate.getJmbg(), candidate.getYearOfBirth(), candidate.getEmail(), candidate.getPhone(), candidate.getNotes(), candidate.getEmployedAfterCompetition(), new Date(), candidate.getId());
        } catch (DataAccessException e) {
            logger.error("Failed to update candidate with ID {}: {}", candidate.getId(), e.getMessage());
            throw e;
        }
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
