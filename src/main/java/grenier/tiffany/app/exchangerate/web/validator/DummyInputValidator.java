package grenier.tiffany.app.exchangerate.web.validator;

import grenier.tiffany.app.exchangerate.exception.InvalidCurrencyException;

import java.time.LocalDate;
import java.util.Currency;

public final class DummyInputValidator implements InputValidator {

    public Currency validateCurrency(final String currency) {
        try {
            return Currency.getInstance(currency);
        } catch (final IllegalArgumentException e) {
            throw new InvalidCurrencyException("Currency " + currency + " is not ISO-4217 compliant", e);
        }
    }

    public LocalDate validateDate(final LocalDate date) {
        return date;
    }
}
