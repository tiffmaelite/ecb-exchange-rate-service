package grenier.tiffany.app.exchangerate.web.validator;

import grenier.tiffany.app.exchangerate.exception.InvalidCurrencyException;
import grenier.tiffany.app.exchangerate.exception.InvalidDateException;
import org.junit.Test;

import java.util.Currency;

import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class EcbInputValidatorTest {

    @Test(expected = InvalidCurrencyException.class)
    public void validateNonExistentCurrency() throws Exception {
        final EcbInputValidator validator = new EcbInputValidator();
        validator.validateCurrency("CHR");
    }

    @Test
    public void validateValidCurrency() throws Exception {
        final EcbInputValidator validator = new EcbInputValidator();
        final Currency result = validator.validateCurrency("CHF");
        assertThat(result, is(Currency.getInstance("CHF")));
    }

    @Test(expected = InvalidDateException.class)
    public void validateDateTooOld() throws Exception {
        final EcbInputValidator validator = new EcbInputValidator();
        validator.validateDate(now().minusDays(200L));
    }

    @Test(expected = InvalidDateException.class)
    public void validateDateInTheFuture() throws Exception {
        final EcbInputValidator validator = new EcbInputValidator();
        validator.validateDate(now().plusDays(1L));
    }

    @Test
    public void validateValidDate() throws Exception {
        final EcbInputValidator validator = new EcbInputValidator();
        validator.validateDate(now().minusDays(10L));
    }

}