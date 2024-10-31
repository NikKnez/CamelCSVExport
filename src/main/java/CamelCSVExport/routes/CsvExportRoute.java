package CamelCSVExport.routes;

import CamelCSVExport.model.Candidate;
import CamelCSVExport.model.SearchCriteria;
import CamelCSVExport.service.CandidateService;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Component
public class CsvExportRoute extends RouteBuilder {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private Environment env;

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .log("Error processing CSV export: ${exception.message}")
                .handled(true)
                .to("file://" + env.getProperty("camel.errorfolder"))
                .log("File moved to Error Folder")
                .end();

        from("direct:exportCandidates")
                .log("Starting CSV export process for filtered candidates")
                .process(exchange -> {
                    SearchCriteria searchCriteria = exchange.getIn().getBody(SearchCriteria.class);
                    List<Candidate> candidateData = candidateService.search(searchCriteria);
                    String csvContent = generateCsvContent(candidateData);
                    exchange.getIn().setBody(csvContent);
                })
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .setHeader(HttpHeaders.CONTENT_DISPOSITION, constant("attachment; filename=filtered_candidates.csv"));

        from("direct:exportAllCandidates")
                .log("Starting CSV export process for all candidates")
                .process(exchange -> {
                    List<Candidate> candidateData = candidateService.getCandidatesForCsvExport();
                    String csvContent = generateCsvContent(candidateData);
                    exchange.getIn().setBody(csvContent);
                })
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .setHeader(HttpHeaders.CONTENT_DISPOSITION, constant("attachment; filename=all_candidates.csv"));
    }

    private String generateCsvContent(List<Candidate> candidates) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("ID,First Name,Last Name,JMBG,Year of Birth,Email,Phone,Notes,Employed After Competition\n");

        for (Candidate candidate : candidates) {
            csvContent.append(String.format("%d,%s,%s,%s,%d,%s,%s,%s,%b\n",
                    candidate.getId(),
                    candidate.getFirstName(),
                    candidate.getLastName(),
                    candidate.getJmbg(),
                    candidate.getYearOfBirth(),
                    candidate.getEmail(),
                    candidate.getPhone(),
                    candidate.getNotes(),
                    candidate.isEmployedAfterCompetition()));
        }
        return csvContent.toString();
    }
}
