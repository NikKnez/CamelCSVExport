package CamelCSVExport.service;

import CamelCSVExport.DAO.CandidateDao;
import CamelCSVExport.model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Candidate> search(Candidate searchCriteria) {
        // Retrieve all candidates from the database or data source
        List<Candidate> allCandidates = candidateDao.findAll();

        // Filter candidates based on the search criteria
        return allCandidates.stream()
                .filter(candidate -> {
                    boolean matches = true;

                    // Check JMBG criteria
                    if (searchCriteria.getJmbg() != null && !searchCriteria.getJmbg().isEmpty()) {
                        matches = candidate.getJmbg().equals(searchCriteria.getJmbg());
                    }

                    // Check Email criteria
                    if (searchCriteria.getEmail() != null && !searchCriteria.getEmail().isEmpty()) {
                        matches = matches && candidate.getEmail().equalsIgnoreCase(searchCriteria.getEmail());
                    }

                    // Check employedAfterCompetition criteria
                    if (searchCriteria.isEmployedAfterCompetition() != null) {
                        matches = matches && candidate.isEmployedAfterCompetition() == searchCriteria.isEmployedAfterCompetition();
                    }

                    return matches;
                })
                .collect(Collectors.toList());
    }

}
