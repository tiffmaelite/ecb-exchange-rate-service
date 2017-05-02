package grenier.tiffany.app.exchangerate.web.validator;

import java.time.LocalDate;
import java.util.Currency;

public final class DummyInputValidator implements InputValidator {

    public Currency validateCurrency(final String currency) throws IllegalArgumentException {
        return Currency.getInstance(currency);
    }

    public LocalDate validateDate(final LocalDate date) {
        return date;
    }
}
