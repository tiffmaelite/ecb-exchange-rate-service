package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.service.store.EuroExchangeRateRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * fetches daily the reference rates from ECB and stores them for later retrieval
 *
 * @link https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
 * @link https://www.ecb.europa.eu/press/pr/date/2015/html/pr151207.en.html
 */
public final class EcbExchangeRateService implements EuroExchangeRateService {//FIXME: rename to avoid confusion with EcbDataService
    private static final Logger LOGGER = getLogger(EcbExchangeRateService.class);
    private static final Currency BASE_CURRENCY = EUR;

    @Autowired
    private EcbDataService dataFetcher;

    @Autowired
    private EuroExchangeRateRepository store;

    public EcbExchangeRateService() {
    }

    //for tests only
    EcbExchangeRateService(final EcbDataService dataFetcher,
                           final EuroExchangeRateRepository store) {
        this.dataFetcher = dataFetcher;
        this.store = store;
    }

    @Override
    public ExchangeRate getExchangeRate(final Currency currency, final LocalDate date) {
        initStorage();
        if (BASE_CURRENCY.equals(currency)) {
            return getIdentityExchangeRate(currency, date);
        }
        return store.get(currency, date);
    }

    @Override
    public ExchangeRate getLatestExchangeRate(final Currency currency) {
        initStorage();
        if (BASE_CURRENCY.equals(currency)) {
            return getIdentityExchangeRate(currency, store.getLatestDate());
        }
        return store.getLatest(currency);
    }

    private void initStorage() {
        if (store.isEmpty()) {
            updateStore();
        }
    }

    private ExchangeRate getIdentityExchangeRate(final Currency currency, final LocalDate date) {
        return new ExchangeRate(currency, currency, date, 1.0);
    }

    @Scheduled(cron = "${scheduling.ecb.job.cron}", zone = "UTC")//file on ECB server is updated at 4pm CET
    public void updateStore() {
        final Future<Collection<ExchangeRate>> result = dataFetcher.fetchHistoricalData();
        try {
            if (result != null) {
                store.save(result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("An error occurred while fetching historical data: {}", e.getMessage(), e);
        }
    }
}