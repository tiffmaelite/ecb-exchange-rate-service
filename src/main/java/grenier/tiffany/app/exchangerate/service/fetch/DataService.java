package grenier.tiffany.app.exchangerate.service.fetch;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.springframework.scheduling.annotation.Async;

import java.util.Collection;
import java.util.concurrent.Future;

public interface DataService {
    @Async
    Future<Collection<ExchangeRate>> fetchHistoricalData();

    @Async
    Future<Collection<ExchangeRate>> fetchLatestData();
}
