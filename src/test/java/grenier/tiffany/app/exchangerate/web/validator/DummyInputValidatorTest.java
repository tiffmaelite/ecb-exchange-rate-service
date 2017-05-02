package grenier.tiffany.app.exchangerate.web.validator;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Currency;

import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class DummyInputValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateNonExistentCurrency() throws Exception {
        final String nonExistentCurrencyCode = "CHR";
        final DummyInputValidator validator = new DummyInputValidator();
        validator.validateCurrency(nonExistentCurrencyCode);
    }

    @Test
    public void validateCurrency() throws Exception {
        final String currencyCode = "USD";
        final DummyInputValidator validator = new DummyInputValidator();
        assertThat(validator.validateCurrency(currencyCode), is(Currency.getInstance(currencyCode)));
    }

    @Test
    public void validateDate() throws Exception {
        final LocalDate date = now();
        final DummyInputValidator validator = new DummyInputValidator();
        assertThat(validator.validateDate(date), is(date));
    }

}