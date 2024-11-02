package CamelCSVExport.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria {
    private String jmbg;
    private String email;
    private Boolean employedAfterCompetition;

    boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (email == null) {
            return false;
        }

        return email.matches(emailRegex);
    }

    public boolean isValidSearchCriteria() {
        boolean valid = this.jmbg == null || this.jmbg.matches("\\d{13}");

        if (this.email != null && !this.isValidEmail(this.email)) {
            valid = false;
        }

        return valid;
    }
}
