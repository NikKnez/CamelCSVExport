package CamelCSVExport.controller;

import CamelCSVExport.DAO.CandidateDao;
import CamelCSVExport.model.Candidate;
import CamelCSVExport.model.SearchCriteria;
import CamelCSVExport.service.CandidateService;
import jakarta.validation.ValidationException;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CandidateControllerTest {

    @Mock
    private CandidateDao candidateDao;

    @Mock
    private ProducerTemplate producerTemplate;

    @Mock
    private CandidateService candidateService;

    @InjectMocks
    private CandidateController candidateController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCandidates() {
        Candidate candidate1 = new Candidate();
        Candidate candidate2 = new Candidate();
        when(candidateDao.findAll()).thenReturn(Arrays.asList(candidate1, candidate2));

        List<Candidate> result = candidateController.getAllCandidates();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        when(candidateDao.findById(1L)).thenReturn(candidate);

        ResponseEntity<Candidate> response = candidateController.getCandidate(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(candidate, response.getBody());
    }

    @Test
    public void testAddCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        ResponseEntity<Candidate> response = candidateController.addCandidate(candidate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(candidate, response.getBody());
        verify(candidateDao).save(candidate);
    }

    @Test
    public void testUpdateCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        when(candidateDao.findById(1L)).thenReturn(candidate);

        ResponseEntity<Candidate> response = candidateController.updateCandidate(1L, candidate);

        assertEquals(200, response.getStatusCodeValue());
        verify(candidateDao).update(candidate);
    }

    @Test
    public void testUpdateCandidateNotFound() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        when(candidateDao.findById(1L)).thenReturn(null);

        ResponseEntity<Candidate> response = candidateController.updateCandidate(1L, candidate);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteCandidate() {
        Long id = 1L;

        ResponseEntity<Void> response = candidateController.deleteCandidate(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(candidateDao).delete(id);
    }

    @Test
    public void testSearchCandidates() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setJmbg("1234567890123");
        when(candidateService.search(any())).thenReturn(Arrays.asList(new Candidate()));

        List<Candidate> result = candidateController.searchCandidates("1234567890123", null, null);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testExportCandidates() {
        byte[] csvContent = "test,csv,data".getBytes();
        when(producerTemplate.requestBody(anyString(), any(), eq(byte[].class))).thenReturn(csvContent);

        ResponseEntity<byte[]> response = candidateController.exportCandidates(null, null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(csvContent, response.getBody());
        assertEquals("attachment; filename=filtered_candidates.csv", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    public void testExportAllCandidates() {
        byte[] csvContent = "test,csv,data".getBytes();
        when(producerTemplate.requestBody(anyString(), isNull(), eq(byte[].class))).thenReturn(csvContent);

        ResponseEntity<byte[]> response = candidateController.exportAllCandidates();

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(csvContent, response.getBody());
        assertEquals("attachment; filename=all_candidates.csv", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
    }
}
