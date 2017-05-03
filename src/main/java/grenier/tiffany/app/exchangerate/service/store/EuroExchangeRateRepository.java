package grenier.tiffany.app.exchangerate.service.store;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.reverseOrder;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class EuroExchangeRateRepository {

    private final Cache<EuroFxRateId, ExchangeRate> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, DAYS)
            .build();
    private LocalDate latestDate;

    public boolean isEmpty() {
        return cache.size() == 0;
    }

    public void save(final Collection<ExchangeRate> exchangeRates) {
        final Map<EuroFxRateId, ExchangeRate> ratesByCurrencyAndDate = exchangeRates.stream()
                .collect(toMap(fxRate -> new EuroFxRateId(fxRate.getConversionDate(), fxRate.getCurrencyTo()), identity()));
        cache.putAll(ratesByCurrencyAndDate);
        latestDate = ratesByCurrencyAndDate.keySet().stream()
                .map(id -> id.conversionDate)
                .sorted(reverseOrder()).findFirst()
                .orElse(null);
    }

    public LocalDate getLatestDate() {
        return latestDate;
    }

    public ExchangeRate get(final Currency currency, final LocalDate date) {
        return cache.getIfPresent(new EuroFxRateId(date, currency));
    }

    public ExchangeRate getLatest(final Currency currency) {
        return get(currency, latestDate);
    }

    private class EuroFxRateId {
        private final LocalDate conversionDate;
        private final Currency currencyTo;

        EuroFxRateId(final LocalDate conversionDate, final Currency currencyTo) {
            this.conversionDate = conversionDate;
            this.currencyTo = currencyTo;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final EuroFxRateId that = (EuroFxRateId) o;
            return Objects.equals(conversionDate, that.conversionDate) &&
                    Objects.equals(currencyTo, that.currencyTo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(conversionDate, currencyTo);
        }
    }
}
