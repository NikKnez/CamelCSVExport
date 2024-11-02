package CamelCSVExport.controller;

import CamelCSVExport.DAO.CandidateDao;
import CamelCSVExport.model.Candidate;
import CamelCSVExport.service.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CandidateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateDao candidateDao;

    @MockBean
    private ProducerTemplate producerTemplate;

    @MockBean
    private CandidateService candidateService;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllCandidates() throws Exception {
        when(candidateDao.findAll()).thenReturn(List.of(new Candidate()));

        mockMvc.perform(get("/api/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddCandidate() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setFirstName("Nik");
        candidate.setLastName("Knez");
        candidate.setJmbg("1234567890123");
        candidate.setYearOfBirth(1986);
        candidate.setEmail("nik.knez@example.com");
        candidate.setPhone("1234567");
        candidate.setNotes("Test candidate");
        candidate.setEmployedAfterCompetition(false);
        candidate.setDataUpdate(LocalDate.now());

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidate)))
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(jsonPath("$.firstName").value("Nik"))
                .andExpect(jsonPath("$.lastName").value("Knez"))
                .andExpect(jsonPath("$.jmbg").value("1234567890123"))
                .andExpect(jsonPath("$.yearOfBirth").value(1986))
                .andExpect(jsonPath("$.email").value("nik.knez@example.com"))
                .andExpect(jsonPath("$.phone").value("1234567"))
                .andExpect(jsonPath("$.notes").value("Test candidate"))
                .andExpect(jsonPath("$.employedAfterCompetition").value(false));
    }

}
