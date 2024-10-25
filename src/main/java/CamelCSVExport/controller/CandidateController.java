package CamelCSVExport.controller;

import CamelCSVExport.DAO.CandidateDao;
import CamelCSVExport.model.Candidate;
import jakarta.validation.Valid;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateDao candidateDao;
    private final ProducerTemplate producerTemplate;

    public CandidateController(CandidateDao candidateDao, ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        this.candidateDao = candidateDao;
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidate(@PathVariable Long id) {
        Candidate candidate = candidateDao.findById(id);
        return ResponseEntity.ok(candidate);
    }

    @PostMapping
    public ResponseEntity<Candidate> addCandidate(@Valid @RequestBody Candidate candidate) {
        candidateDao.save(candidate);
        return ResponseEntity.ok(candidate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @Valid @RequestBody Candidate candidate) {
        if (candidateDao.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        candidate.setId(id);
        candidateDao.update(candidate);
        return ResponseEntity.ok(candidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateDao.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Candidate> searchCandidates(
            @RequestParam(required = false) String jmbg,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean employedAfterCompetition) {
        Candidate searchCriteria = new Candidate();
        searchCriteria.setJmbg(jmbg);
        searchCriteria.setEmail(email);
        searchCriteria.setEmployedAfterCompetition(employedAfterCompetition != null && employedAfterCompetition);

        return candidateDao.search(searchCriteria);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> exportCandidates(
            @RequestParam(required = false) String jmbg,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean employedAfterCompetition) {

        Candidate searchCriteria = new Candidate();
        searchCriteria.setJmbg(jmbg);
        searchCriteria.setEmail(email);
        searchCriteria.setEmployedAfterCompetition(employedAfterCompetition);

        byte[] csvContent = producerTemplate.requestBody("direct:exportCandidates", searchCriteria, byte[].class);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=candidates.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(csvContent);
    }


}
