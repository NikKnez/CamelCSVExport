package CamelCSVExport.service;

import CamelCSVExport.DAO.CandidateDao;
import CamelCSVExport.model.Candidate;
import CamelCSVExport.model.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CandidateServiceIntegrationTest {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateDao candidateDao;

    @Test
    void testAddAndGetCandidate() {
        Candidate candidate = new Candidate();
        candidate.setJmbg("1234567890123");
        candidateService.addCandidate(candidate);

        List<Candidate> candidates = candidateService.getCandidatesForCsvExport();
        assertNotNull(candidates);
        assertFalse(candidates.isEmpty());
    }

    @Test
    void testGetCandidateByJMBG() {
        SearchCriteria candidate = new SearchCriteria();
        candidate.setJmbg("1234567890123");
        candidateDao.search(candidate);

        Candidate foundCandidate = candidateService.getCandidateByJMBG("1234567890123");
        assertNotNull(foundCandidate);
    }
}
