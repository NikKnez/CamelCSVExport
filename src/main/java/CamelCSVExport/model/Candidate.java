package CamelCSVExport.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Candidate {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Size(min = 13, max = 13, message = "JMBG must be 13 digits long")
    private String jmbg;

    private int yearOfBirth;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    private String notes;

    private Boolean employedAfterCompetition;

    private LocalDate dataUpdate;

    public Object isEmployedAfterCompetition() {
        return employedAfterCompetition;
    }

}
