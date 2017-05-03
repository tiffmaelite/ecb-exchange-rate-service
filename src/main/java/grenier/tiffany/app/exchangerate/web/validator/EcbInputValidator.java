package grenier.tiffany.app.exchangerate.web.validator;

import grenier.tiffany.app.exchangerate.exception.InvalidCurrencyException;
import grenier.tiffany.app.exchangerate.exception.InvalidDateException;

import java.time.LocalDate;
import java.util.Currency;

import static java.time.LocalDate.now;
import static java.util.Optional.ofNullable;

public final class EcbInputValidator implements InputValidator {

    private static final long MAX_DAYS_IN_PAST = 90L;

    @Override
    public Currency validateCurrency(final String currency) {
        try {
            //TODO: check that the currency exists in ECB data
            return Currency.getInstance(currency);
        } catch (final IllegalArgumentException e) {
            throw new InvalidCurrencyException("Currency " + currency + " is not ISO-4217 compliant", e);
        }
    }

    @Override
    public LocalDate validateDate(final LocalDate date) {
        return ofNullable(date)
                .filter(this::isDateValid)
                .orElseThrow(() -> new InvalidDateException("Date should not be in the future and should not be older than " + MAX_DAYS_IN_PAST + " days"));
    }

    private boolean isDateValid(final LocalDate d) {
        final LocalDate today = now();
        return (d.equals(today) || d.isBefore(today))
                && d.isAfter(today.minusDays(MAX_DAYS_IN_PAST));//FIXME: what if today is not a workweek day? how many days in teh past are then really allowed?
    }
}
