package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Currency;

import static grenier.tiffany.app.exchangerate.service.EuroExchangeRateService.EUR;
import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public final class DummyEuroExchangeRateServiceTest {

    @Test
    public void getExchangeRate() throws Exception {
        final Currency testCurrency = Currency.getInstance("CHF");
        final LocalDate testDate = now();

        final DummyEuroExchangeRateService service = new DummyEuroExchangeRateService();
        final ExchangeRate exchangeRate = service.getExchangeRate(testCurrency, testDate);
        assertThat(exchangeRate, is(notNullValue()));

        assertThat(exchangeRate.getConversionDate(), is(testDate));
        assertThat(exchangeRate.getCurrencyTo(), is(testCurrency));
        assertThat(exchangeRate.getCurrencyFrom(), is(EUR));

        assertThat(exchangeRate.getConversionRate(), is(anyOf(equalTo(1.0), closeTo(1.0, 1e-4))));
    }

}