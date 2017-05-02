package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;

import static java.time.LocalDate.now;


/**
 * dummy implementation for the exchange rate service that always returns a rate of 1
 */
@Service
public final class DummyEuroExchangeRateService implements EuroExchangeRateService {

    private static final double DUMMY_IDENTITY_RATE = 1.0;

    @Override
    public ExchangeRate getExchangeRate(final Currency currency, final LocalDate date) {
        return createDummyData(currency, date);
    }

    @Override
    public ExchangeRate getLatestExchangeRate(final Currency currency) {
        return createDummyData(currency, now());
    }

    private ExchangeRate createDummyData(final Currency currency, final LocalDate date) {
        return new ExchangeRate(EUR, currency, date, DUMMY_IDENTITY_RATE);
    }
}
