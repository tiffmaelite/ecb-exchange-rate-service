package grenier.tiffany.app.exchangerate.service.fetch;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.Future;

/**
 * Reads the Euro fx rates from the European Central Bank rest endpoint located at
 * <a href="https://sdw-wsrest.ecb.europa.eu/service/data">https://sdw-wsrest.ecb.europa.eu/service/data</a>
 */
@Service
public class EcbRestDataService implements DataService {
    @Override
    public Future<Collection<ExchangeRate>> fetchHistoricalData() {
        return null;//TODO
    }

    @Override
    public Future<Collection<ExchangeRate>> fetchLatestData() {
        return null;//TODO
    }
}
