package grenier.tiffany.app.exchangerate.adapter;

import com.google.common.collect.ImmutableList;
import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.model.ecb.DatedEuroExchangeRates;
import grenier.tiffany.app.exchangerate.model.ecb.EcbReferenceRates;
import grenier.tiffany.app.exchangerate.model.ecb.EuroExchangeRate;
import grenier.tiffany.app.exchangerate.model.ecb.EuroExchangeRateTimeSeries;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static grenier.tiffany.app.exchangerate.service.EuroExchangeRateService.EUR;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

//TODO: use Spock?
public final class EcbAdapterTest {

    @Test
    public void adaptEcbReferenceRates() {
        final EcbAdapter adapter = new EcbAdapter();
        final Collection<ExchangeRate> fxRates = adapter.adapt(createEcbReferenceRates());

        assertThat(fxRates, is(notNullValue()));
        assertThat(fxRates.size(), is(5));

        final ExchangeRate firstExchangeRate = newArrayList(fxRates).get(0);
        assertThat(firstExchangeRate.getCurrencyFrom(), is(EUR));
        assertThat(firstExchangeRate.getCurrencyTo(), is(Currency.getInstance(CHF)));
        assertThat(firstExchangeRate.getConversionDate(), is(LocalDate.parse(DATE_1)));
        assertThat(firstExchangeRate.getConversionRate(), is(anyOf(equalTo(CHF_RATE_1), closeTo(CHF_RATE_1, 1e-4))));
    }

    @Test
    public void adaptDatedEuroExchangeRates() {
        final EcbAdapter adapter = new EcbAdapter();
        final Stream<ExchangeRate> rates = adapter.adapt(createDatedEuroExchangeRates2());

        assertThat(rates, is(notNullValue()));
        final List<ExchangeRate> list = rates.collect(toList());
        assertThat(list.size(), is(2));

        final ExchangeRate firstExchangeRate = list.get(0);
        assertThat(firstExchangeRate.getCurrencyFrom(), is(EUR));
        assertThat(firstExchangeRate.getCurrencyTo(), is(Currency.getInstance(CHF)));
        assertThat(firstExchangeRate.getConversionDate(), is(LocalDate.parse(DATE_2)));
        assertThat(firstExchangeRate.getConversionRate(), is(anyOf(equalTo(CHF_RATE_2), closeTo(CHF_RATE_2, 1e-4))));
    }

    @Test
    public void adaptEuroExchangeRate() {
        final EcbAdapter adapter = new EcbAdapter();
        final ExchangeRate rate = adapter.adapt(LocalDate.parse(DATE_1), createEuroExchangeRate(USD, USD_RATE_1));

        assertThat(rate, is(notNullValue()));
        
        assertThat(rate.getCurrencyFrom(), is(EUR));
        assertThat(rate.getCurrencyTo(), is(Currency.getInstance(USD)));
        assertThat(rate.getConversionDate(), is(LocalDate.parse(DATE_1)));
        assertThat(rate.getConversionRate(), is(anyOf(equalTo(USD_RATE_1), closeTo(USD_RATE_1, 1e-4))));
    }


    /**
     * test data constellation
     */
    //TODO: use builder pattern
    //TODO: use Spock?

    private static final String CHF = "CHF";
    private static final String USD = "USD";
    private static final String DKK = "DKK";
    private static final String DATE_1 = "2015-04-05";
    private static final String DATE_2 = "2018-03-03";
    private static final double CHF_RATE_1 = 1.2;
    private static final double USD_RATE_1 = 0.9;
    private static final double DKK_RATE_1 = 1.0;
    private static final double CHF_RATE_2 = 1.1;
    private static final double USD_RATE_2 = 0.9674328;

    private EcbReferenceRates createEcbReferenceRates() {
        final EcbReferenceRates result = new EcbReferenceRates();
        result.setEuroExchangeRateTimeSeries(createEuroExchangeRateTimeSeries());
        return result;
    }

    private EuroExchangeRateTimeSeries createEuroExchangeRateTimeSeries() {
        final EuroExchangeRateTimeSeries result = new EuroExchangeRateTimeSeries();
        result.setCube(ImmutableList.of(
                createDatedEuroExchangeRates1(),
                createDatedEuroExchangeRates2()
        ));
        return result;
    }

    private DatedEuroExchangeRates createDatedEuroExchangeRates1() {
        final DatedEuroExchangeRates result = new DatedEuroExchangeRates();
        result.setCube(ImmutableList.of(
                createEuroExchangeRate(CHF, CHF_RATE_1),
                createEuroExchangeRate(USD, USD_RATE_1),
                createEuroExchangeRate(DKK, DKK_RATE_1)));
        result.setTime(DATE_1);
        return result;
    }

    private DatedEuroExchangeRates createDatedEuroExchangeRates2() {
        final DatedEuroExchangeRates result = new DatedEuroExchangeRates();
        result.setCube(ImmutableList.of(
                createEuroExchangeRate(CHF, CHF_RATE_2),
                createEuroExchangeRate(USD, USD_RATE_2)));
        result.setTime(DATE_2);
        return result;
    }

    private EuroExchangeRate createEuroExchangeRate(final String currency, final double rate) {
        final EuroExchangeRate result = new EuroExchangeRate();
        result.setCurrency(currency);
        result.setRate(rate);
        return result;
    }
}