package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class EcbDataServiceIT {

    @Test
    public void fetchHistoricalData() {
        final EcbDataService service = new EcbDataService();
        final Future<Collection<ExchangeRate>> result = service.fetchHistoricalData();
        try {
            final Collection<ExchangeRate> rates = result.get();
            assertThat(rates, is(notNullValue()));
            assertThat(rates, is(not(empty())));
        } catch (final InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void fetchLatestData() {
        final EcbDataService service = new EcbDataService();
        final Future<Collection<ExchangeRate>> result = service.fetchLatestData();
        try {
            final Collection<ExchangeRate> rates = result.get();
            assertThat(rates, is(notNullValue()));
            assertThat(rates, is(not(empty())));
        } catch (final InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }
}