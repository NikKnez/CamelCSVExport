package CamelCSVExport.DAO;

import CamelCSVExport.mapper.CandidateRowMapper;
import CamelCSVExport.model.Candidate;
import CamelCSVExport.model.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CandidateDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CandidateDao candidateDao;

    private Candidate candidate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        candidate = new Candidate();
        candidate.setId(1L);
        candidate.setFirstName("Nik");
        candidate.setLastName("Knez");
        candidate.setJmbg("1234567890123");
        candidate.setEmail("nik.knez@example.com");
    }

    @Test
    void testFindAll() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);

        when(jdbcTemplate.query(anyString(), any(CandidateRowMapper.class))).thenReturn(candidates);

        List<Candidate> result = candidateDao.findAll();
        assertEquals(1, result.size());
        assertEquals("Nik", result.get(0).getFirstName());
    }

    @Test
    void testFindById() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(CandidateRowMapper.class))).thenReturn(candidate);

        Candidate result = candidateDao.findById(1L);
        assertEquals("Nik", result.getFirstName());
    }

    @Test
    void testSave() {
        Candidate candidate = new Candidate();
        candidate.setEmail("test@example.com");
        candidate.setJmbg("1234567890123");

        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        candidateDao.update(candidate);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    void testUpdate() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        candidateDao.update(candidate);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    void testDelete() {
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        candidateDao.update(candidate);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    void testSearch() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setJmbg("1234567890123");

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(CandidateRowMapper.class))).thenReturn(candidates);

        List<Candidate> result = candidateDao.search(searchCriteria);
        assertEquals(1, result.size());
        assertEquals("Nik", result.getFirst().getFirstName());
    }

    @Test
    void testFindByJmbg() {
        when(jdbcTemplate.queryForObject(anyString(), any(), any(CandidateRowMapper.class))).thenReturn(candidate);

        Candidate result = (Candidate) candidateDao.findByJmbg("1234567890123");
        assertEquals("Nik", result.getFirstName());
    }

    @Test
    void testFindByEmail() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(CandidateRowMapper.class))).thenReturn(candidates);

        List<Candidate> result = candidateDao.findByEmail("nik.knez@example.com");
        assertEquals(1, result.size());
        assertEquals("Nik", result.get(0).getFirstName());
    }

    @Test
    void testFindByEmployedAfterCompetition() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(CandidateRowMapper.class))).thenReturn(candidates);

        List<Candidate> result = candidateDao.findByEmployedAfterCompetition(true);
        assertEquals(1, result.size());
        assertEquals("Nik", result.get(0).getFirstName());
    }
}
