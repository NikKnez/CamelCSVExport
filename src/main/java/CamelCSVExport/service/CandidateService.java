package CamelCSVExport.service;

import CamelCSVExport.DAO.CandidateDao;
import CamelCSVExport.model.Candidate;
import CamelCSVExport.model.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateDao candidateDao;

    public List<Candidate> getCandidatesForCsvExport() {
        return candidateDao.findAll();
    }

    public Candidate getCandidateById(Long id) {
        return candidateDao.findById(id);
    }

    public Candidate getCandidateByJMBG(String jmbg) {
        return candidateDao.findByJmbg(jmbg);
    }

    public List<Candidate> getCandidatesByEmail(String email) {
        return candidateDao.findByEmail(email);
    }

    public List<Candidate> getCandidatesByEmploymentStatus(boolean employedAfterCompetition) {
        return candidateDao.findByEmployedAfterCompetition(employedAfterCompetition);
    }

    @Transactional
    public void addCandidate(Candidate candidate) {
        candidateDao.save(candidate);
    }

    @Transactional
    public void updateCandidate(Candidate candidate) {
        candidateDao.update(candidate);
    }

    @Transactional
    public void deleteCandidate(Long id) {
        candidateDao.delete(id);
    }

    public List<Candidate> search(SearchCriteria searchCriteria) {
        return candidateDao.search(searchCriteria);
    }

}
