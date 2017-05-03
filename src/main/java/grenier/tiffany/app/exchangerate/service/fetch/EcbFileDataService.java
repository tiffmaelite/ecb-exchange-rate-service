package grenier.tiffany.app.exchangerate.service.fetch;

import grenier.tiffany.app.exchangerate.adapter.EcbReferenceRatesAdapter;
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
import java.util.Collection;
import java.util.concurrent.Future;

import static java.util.Collections.emptyList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Reads the Euro fx rates from the European Central Bank data feed located at
 * <a href="http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml">http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml</a> and
 * <a href="http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml">http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml</a>
 */
@Service
public class EcbFileDataService implements DataService {

    private static final Logger LOGGER = getLogger(EcbFileDataService.class);

    private static final String URL_DAILY = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final String URL_HISTORY = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";

    private Unmarshaller unmarshaller;

    public EcbFileDataService() {
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

    @Override
    @Async
    public Future<Collection<ExchangeRate>> fetchHistoricalData() {
        try {
            return new AsyncResult<>(loadConversionRates(new URL(URL_HISTORY)));
        } catch (final MalformedURLException e) {
            LOGGER.error("Unable to access data at " + URL_HISTORY + ": {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Async
    public Future<Collection<ExchangeRate>> fetchLatestData() {
        try {
            return new AsyncResult<>(loadConversionRates(new URL(URL_DAILY)));
        } catch (final MalformedURLException e) {
            LOGGER.error("Unable to access data at " + URL_DAILY + ": {}", e.getMessage(), e);
            return null;
        }
    }

    Collection<ExchangeRate> loadConversionRates(final URL url) {
        try (InputStream is = url.openStream()) {
            final EcbReferenceRates ecbRates = (EcbReferenceRates) unmarshaller.unmarshal(new StreamSource(is));
            return new EcbReferenceRatesAdapter().adapt(ecbRates);
        } catch (final IOException ioe) {
            LOGGER.error("An error occured while loading conversion rates: {}", ioe.getMessage(), ioe);
            return emptyList();
        }
    }
}