package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;


/**
 * dummy implementation for the exchange rate store that always returns a rate of 1
 */
@Service
public final class DummyEuroExchangeRateService implements EuroExchangeRateService {

    private static final double DUMMY_IDENTITY_RATE = 1.0;

    @Override
    public ExchangeRate getExchangeRate(final Currency currency, final LocalDate date) {
        return new ExchangeRate(EUR, currency, date, DUMMY_IDENTITY_RATE);
    }
}
