package CamelCSVExport.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchCriteriaTest {

    @Test
    public void testValidSearchCriteria_withValidJmbgAndEmail() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setJmbg("1234567890123");
        searchCriteria.setEmail("test@example.com");

        boolean result = searchCriteria.isValidSearchCriteria();

        assertThat(result).isTrue();
    }

    @Test
    public void testValidSearchCriteria_withInvalidJmbg() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setJmbg("123"); // invalid JMBG (not 13 digits)
        searchCriteria.setEmail("test@example.com");

        boolean result = searchCriteria.isValidSearchCriteria();

        assertThat(result).isFalse();
    }

    @Test
    public void testValidSearchCriteria_withInvalidEmail() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setJmbg("1234567890123");
        searchCriteria.setEmail("invalid-email"); // invalid email

        boolean result = searchCriteria.isValidSearchCriteria();

        assertThat(result).isFalse();
    }

    @Test
    public void testValidSearchCriteria_withNullJmbgAndEmail() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setJmbg(null);
        searchCriteria.setEmail(null);

        boolean result = searchCriteria.isValidSearchCriteria();

        assertThat(result).isTrue();
    }

    @Test
    public void testIsValidEmail_withValidEmail() {
        SearchCriteria searchCriteria = new SearchCriteria();

        boolean result = searchCriteria.isValidEmail("valid.email@example.com");

        assertThat(result).isTrue();
    }

    @Test
    public void testIsValidEmail_withInvalidEmail() {
        SearchCriteria searchCriteria = new SearchCriteria();

        boolean result = searchCriteria.isValidEmail("invalid-email");

        assertThat(result).isFalse();
    }

    @Test
    public void testIsValidEmail_withNullEmail() {
        SearchCriteria searchCriteria = new SearchCriteria();

        boolean result = searchCriteria.isValidEmail(null);

        assertThat(result).isFalse();
    }
}
