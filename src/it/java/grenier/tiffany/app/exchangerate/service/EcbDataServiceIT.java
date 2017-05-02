package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Currency;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class EcbDataServiceIT {

    @Test
    public void fetchHistoricalData() {
        final EcbDataService service = new EcbDataService();
        final Future<ExchangeRate> result = service.fetchHistoricalData(Currency.getInstance("CHF"), LocalDate.parse("2017-01-03"));
        try {
            final ExchangeRate rate = result.get();
            assertThat(rate, is(notNullValue()));
        } catch (final InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void fetchLatestData() {
        final EcbDataService service = new EcbDataService();
        final Future<ExchangeRate> result = service.fetchLatestData(Currency.getInstance("CHF"));
        try {
            final ExchangeRate rate = result.get();
            assertThat(rate, is(notNullValue()));
        } catch (final InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }
}