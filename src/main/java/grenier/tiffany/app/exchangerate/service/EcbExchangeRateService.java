package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Currency;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;


public final class EcbExchangeRateService implements EuroExchangeRateService {//FIXME: rename to avoid confusion with EcbDataService
    private static final Logger LOGGER = getLogger(EcbExchangeRateService.class);

    @Autowired
    private EcbDataService dataFetcher;

    @Override
    public ExchangeRate getExchangeRate(final Currency currency, final LocalDate date) {
        //TODO: use store
        final Future<ExchangeRate> result = dataFetcher.fetchHistoricalData(currency, date);
        try {
            if (result != null) {
                return result.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("An error occurred while fetching historical data: {}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ExchangeRate getLatestExchangeRate(final Currency currency) {
        //TODO: use store
        final Future<ExchangeRate> result = dataFetcher.fetchLatestData(currency);
        try {
            if (result != null) {
                return result.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("An error occurred while latest historical data: {}", e.getMessage(), e);
        }
        return null;
    }
}