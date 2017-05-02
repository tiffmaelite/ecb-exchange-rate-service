package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.adapter.EcbAdapter;
import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.model.ecb.DatedEuroExchangeRates;
import grenier.tiffany.app.exchangerate.model.ecb.EcbReferenceRates;
import grenier.tiffany.app.exchangerate.model.ecb.EuroExchangeRate;
import grenier.tiffany.app.exchangerate.model.ecb.EuroExchangeRateTimeSeries;
import org.slf4j.Logger;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.concurrent.Future;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.Collections.emptyList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Reads the Euro fx rates from the European Central Bank data feeds
 */
@Service
public class EcbDataService {

    private static final Logger LOGGER = getLogger(EcbDataService.class);

    private static final String URL_DAILY = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final String URL_HISTORY = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";

    private Unmarshaller unmarshaller;

    public EcbDataService() {
        unmarshaller = createUnmarshaller();
    }

    private Unmarshaller createUnmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(
                EcbReferenceRates.class,
                EuroExchangeRateTimeSeries.class,
                DatedEuroExchangeRates.class,
                EuroExchangeRate.class);
        return marshaller;
    }

    @Async
    Future<ExchangeRate> fetchHistoricalData(final Currency currency, final LocalDate date) {
        try {
            final Collection<ExchangeRate> exchangeRates = loadConversionRates(new URL(URL_HISTORY));
            final ExchangeRate result = exchangeRates.stream()
                    .filter(r -> r.getConversionDate().equals(date))
                    .filter(r -> r.getCurrencyTo().equals(currency))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No exchange rate for currency " + currency.getCurrencyCode() + " at date " + date.format(ISO_DATE)));
            return new AsyncResult<>(result);
        } catch (final MalformedURLException e) {
            LOGGER.error("Unable to access data at " + URL_HISTORY + ": {}", e.getMessage(), e);
            return null;
        }
    }

    @Async
    Future<ExchangeRate> fetchLatestData(final Currency currency) {
        try {
            final Collection<ExchangeRate> exchangeRates = loadConversionRates(new URL(URL_DAILY));
            final ExchangeRate result = exchangeRates.stream()
                    .filter(r -> r.getCurrencyTo().equals(currency))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No exchange rate for currency " + currency.getCurrencyCode()));
            return new AsyncResult<>(result);
        } catch (final MalformedURLException e) {
            LOGGER.error("Unable to access data at " + URL_DAILY + ": {}", e.getMessage(), e);
            return null;
        }
    }

    Collection<ExchangeRate> loadConversionRates(final URL url) {
        try (InputStream is = url.openStream()) {
            final EcbReferenceRates ecbRates = (EcbReferenceRates) unmarshaller.unmarshal(new StreamSource(is));
            return new EcbAdapter().adapt(ecbRates);
        } catch (final IOException ioe) {
            LOGGER.error("An error occured while loading conversion rates: {}", ioe.getMessage(), ioe);
            return emptyList();
        }
    }
}