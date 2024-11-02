package CamelCSVExport.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CandidateTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCandidate() {
        Candidate candidate = new Candidate();
        candidate.setFirstName("Nik");
        candidate.setLastName("Knez");
        candidate.setJmbg("1234567890123");
        candidate.setYearOfBirth(1985);
        candidate.setEmail("nik.knez@example.com");
        candidate.setPhone("555-1234");
        candidate.setNotes("Some notes");
        candidate.setEmployedAfterCompetition(true);
        candidate.setDataUpdate(LocalDate.now());

        Set<ConstraintViolation<Candidate>> violations = validator.validate(candidate);
        assertTrue(violations.isEmpty(), "Candidate should be valid with correct data");
    }

    @Test
    void testInvalidEmailFormat() {
        Candidate candidate = new Candidate();
        candidate.setFirstName("Nik");
        candidate.setLastName("Knez");
        candidate.setJmbg("1234567890123"); // valid JMBG
        candidate.setYearOfBirth(1990);
        candidate.setEmail("invalidEmail"); // invalid email format

        Set<ConstraintViolation<Candidate>> violations = validator.validate(candidate);
        assertEquals(1, violations.size(), "Expected 1 validation error for email format");
    }

    @Test
    void testBlankFirstName() {
        Candidate candidate = new Candidate();
        candidate.setFirstName(""); // invalid: blank first name
        candidate.setLastName("Knez");
        candidate.setJmbg("1234567890123"); // valid JMBG
        candidate.setYearOfBirth(1990);
        candidate.setEmail("nik.knez@example.com"); // valid email

        Set<ConstraintViolation<Candidate>> violations = validator.validate(candidate);
        assertEquals(1, violations.size(), "Expected 1 validation error for blank first name");
    }

    @Test
    void testInvalidJmbgLength() {
        Candidate candidate = new Candidate();
        candidate.setFirstName("Nik");
        candidate.setLastName("Knez");
        candidate.setJmbg("123"); // invalid JMBG length
        candidate.setYearOfBirth(1990);
        candidate.setEmail("nik.knez@example.com"); // valid email

        Set<ConstraintViolation<Candidate>> violations = validator.validate(candidate);
        assertEquals(1, violations.size(), "Expected 1 validation error for JMBG length");
    }

    @Test
    void testNullEmployedAfterCompetition() {
        Candidate candidate = new Candidate();
        candidate.setFirstName("Nik");
        candidate.setLastName("Knez");
        candidate.setJmbg("1234567890123"); // valid JMBG
        candidate.setYearOfBirth(1990);
        candidate.setEmail("nik.knez@example.com"); // valid email

        Set<ConstraintViolation<Candidate>> violations = validator.validate(candidate);
        assertTrue(violations.isEmpty(), "Candidate should be valid without employedAfterCompetition specified");
    }
}

