package grenier.tiffany.app.exchangerate.web.validator;

import java.time.LocalDate;
import java.util.Currency;

import static java.time.LocalDate.now;
import static java.util.Optional.ofNullable;

public final class EcbInputValidator implements InputValidator {

    private static final long MAX_DAYS_IN_PAST = 90L;

    @Override
    public Currency validateCurrency(final String currency) throws IllegalArgumentException {
        return Currency.getInstance(currency);
    }

    @Override
    public LocalDate validateDate(final LocalDate date) throws IllegalArgumentException {
        final LocalDate today = now();
        return ofNullable(date)
                .filter(d -> (d.equals(today) || d.isBefore(today))
                        && d.isAfter(today.minusDays(MAX_DAYS_IN_PAST)))//TODO: what if today is not a workweek day?
                .orElseThrow(() -> new IllegalArgumentException("Invalid date"));
    }
}
