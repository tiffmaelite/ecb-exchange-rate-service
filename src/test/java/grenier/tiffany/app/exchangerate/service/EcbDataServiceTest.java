package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.junit.Test;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

import static grenier.tiffany.app.exchangerate.service.EuroExchangeRateService.EUR;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public final class EcbDataServiceTest {
    @Test
    public void loadDailyConversionRates() throws Exception {
        final EcbDataService service = new EcbDataService();
        final URL path = getClass().getClassLoader().getResource("xml/eurofxref-daily.xml");
        final Collection<ExchangeRate> rates = service.loadConversionRates(path);
        assertThat(rates, is(notNullValue()));
        assertThat(rates, is(not(empty())));
        assertThat(rates.size(), is(31));
        assertThat(((List) rates).get(0), is(new ExchangeRate(EUR, Currency.getInstance("USD"), LocalDate.parse("2017-04-28"), 1.0930)));
    }

    @Test
    public void loadHistoricalConversionRates() throws Exception {
        final EcbDataService service = new EcbDataService();
        final URL path = getClass().getClassLoader().getResource("xml/eurofxref-hist-90d.xml");
        final Collection<ExchangeRate> rates = service.loadConversionRates(path);
        assertThat(rates, is(notNullValue()));
        assertThat(rates, is(not(empty())));
        assertThat(rates.size(), is(1953));
        assertThat(((List) rates).get(0), is(new ExchangeRate(EUR, Currency.getInstance("USD"), LocalDate.parse("2017-04-28"), 1.0930)));
    }
}