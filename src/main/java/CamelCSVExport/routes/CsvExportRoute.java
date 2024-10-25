package CamelCSVExport.routes;

import CamelCSVExport.model.Candidate;
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
                .log("Starting CSV export process")
                .process(exchange -> {
                    List<Candidate> candidateData = candidateService.getCandidatesForCsvExport();
                    StringBuilder csvContent = new StringBuilder();

                    csvContent.append("ID,First Name,Last Name,JMBG,Year of Birth,Email,Phone,Notes,Employed After Competition\n");

                    for (Candidate candidate : candidateData) {
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

                    exchange.getIn().setBody(csvContent.toString());
                })
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .setHeader(HttpHeaders.CONTENT_DISPOSITION, constant("attachment; filename=candidates.csv"));


    }


}