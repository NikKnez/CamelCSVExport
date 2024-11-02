package CamelCSVExport.service;

import CamelCSVExport.DAO.CandidateDao;
import CamelCSVExport.model.Candidate;
import CamelCSVExport.model.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateServiceTest {

    @Mock
    private CandidateDao candidateDao;

    @InjectMocks
    private CandidateService candidateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCandidatesForCsvExport() {
        Candidate candidate1 = new Candidate();
        Candidate candidate2 = new Candidate();
        when(candidateDao.findAll()).thenReturn(Arrays.asList(candidate1, candidate2));

        List<Candidate> candidates = candidateService.getCandidatesForCsvExport();

        assertEquals(2, candidates.size());
        verify(candidateDao, times(1)).findAll();
    }

    @Test
    void testGetCandidateById() {
        Long candidateId = 1L;
        Candidate candidate = new Candidate();
        when(candidateDao.findById(candidateId)).thenReturn(candidate);

        Candidate foundCandidate = candidateService.getCandidateById(candidateId);

        assertNotNull(foundCandidate);
        verify(candidateDao, times(1)).findById(candidateId);
    }

    @Test
    void testAddCandidate() {
        Candidate candidate = new Candidate();
        candidateService.addCandidate(candidate);
        verify(candidateDao, times(1)).save(candidate);
    }

    @Test
    void testDeleteCandidate() {
        Long candidateId = 1L;
        candidateService.deleteCandidate(candidateId);
        verify(candidateDao, times(1)).delete(candidateId);
    }

    @Test
    void testSearch() {
        SearchCriteria criteria = new SearchCriteria();
        Candidate candidate1 = new Candidate();
        when(candidateDao.search(criteria)).thenReturn(Arrays.asList(candidate1));

        List<Candidate> results = candidateService.search(criteria);

        assertEquals(1, results.size());
        verify(candidateDao, times(1)).search(criteria);
    }
}
