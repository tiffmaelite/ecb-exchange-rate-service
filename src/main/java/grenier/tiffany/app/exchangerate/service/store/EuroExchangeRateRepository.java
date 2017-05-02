package grenier.tiffany.app.exchangerate.service.store;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.Objects;

import static java.util.Collections.reverseOrder;

@Service
//TODO: proper thread-safe caching solution
public class EuroExchangeRateRepository {

    private final Table<LocalDate, Currency, ExchangeRate> table = HashBasedTable.create();
    private LocalDate latestDate;

    public boolean isEmpty() {
        return latestDate == null || table.isEmpty();
    }

    /**
     * saves atomically the given rates
     *
     * @param exchangeRates fxRates to save
     */
    public synchronized void save(final Collection<ExchangeRate> exchangeRates) {
        table.clear();
        table.putAll(getExchangeRatesByCurrencyAndDate(exchangeRates));
        latestDate = table.rowKeySet().stream().sorted(reverseOrder()).findFirst().orElse(null);
    }

    private Table<LocalDate, Currency, ExchangeRate> getExchangeRatesByCurrencyAndDate(final Collection<ExchangeRate> exchangeRates) {
        final Table<LocalDate, Currency, ExchangeRate> resultTable = HashBasedTable.create();
        for (final ExchangeRate fxRate : exchangeRates) {
            resultTable.put(fxRate.getConversionDate(), fxRate.getCurrencyTo(), fxRate);
        }
        return resultTable;
    }

    public synchronized LocalDate getLatestDate() {
        return latestDate;
    }

    public synchronized ExchangeRate get(final Currency currency, final LocalDate date) {
        return table.get(date, currency);
    }

    public synchronized ExchangeRate getLatest(final Currency currency) {
        return get(currency, latestDate);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EuroExchangeRateRepository{");
        sb.append("table=").append(table);
        sb.append(", latestDate=").append(latestDate);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EuroExchangeRateRepository that = (EuroExchangeRateRepository) o;
        return Objects.equals(table, that.table) &&
                Objects.equals(latestDate, that.latestDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, latestDate);
    }
}
