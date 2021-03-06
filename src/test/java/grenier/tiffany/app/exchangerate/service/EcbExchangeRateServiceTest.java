package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.service.fetch.EcbFileDataService;
import grenier.tiffany.app.exchangerate.service.store.EuroExchangeRateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.annotation.AsyncResult;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

import static grenier.tiffany.app.exchangerate.service.EuroExchangeRateService.EUR;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//TODO: use Spock?
public class EcbExchangeRateServiceTest {

    @Mock
    private EcbFileDataService mockDataService;

    @Mock
    private EuroExchangeRateRepository mockRepository;

    @Test
    public void getExchangeRateOnAlreadyFilledStoreShouldReadFromStore() {
        final Currency currency = Currency.getInstance("GBP");
        final LocalDate date = LocalDate.parse("2016-08-03");
        final double rate = 0.83705;

        final EcbExchangeRateService service = new EcbExchangeRateService(mockDataService, mockRepository);
        final ExchangeRate expectedResult = new ExchangeRate(EUR, currency, date, rate);
        when(mockRepository.isEmpty()).thenReturn(false);
        when(mockRepository.get(currency, date)).thenReturn(Optional.of(expectedResult));

        final ExchangeRate actualResult = service.getExchangeRate(currency, date);
        assertThat(actualResult, is(expectedResult));

        verify(mockDataService, times(0)).fetchHistoricalData();
        verify(mockDataService, times(0)).fetchLatestData();
    }

    @Test
    public void getExchangeRateOnEmptyStoreShouldFetchData() {
        final Currency currency = Currency.getInstance("GBP");
        final LocalDate date = LocalDate.parse("2016-08-03");
        final double rate = 0.83705;

        final EcbExchangeRateService service = new EcbExchangeRateService(mockDataService, mockRepository);
        final ExchangeRate expectedResult = new ExchangeRate(EUR, currency, date, rate);
        when(mockRepository.isEmpty()).thenReturn(true);

        when(mockDataService.fetchHistoricalData()).thenReturn(new AsyncResult<>(singleton(expectedResult)));
        when(mockRepository.get(currency, date)).thenReturn(Optional.of(expectedResult));

        service.getExchangeRate(currency, date);

        verify(mockDataService, times(1)).fetchHistoricalData();
        verify(mockDataService, times(0)).fetchLatestData();
    }

    @Test
    public void updateStore() {
        final EcbExchangeRateService service = new EcbExchangeRateService(mockDataService, mockRepository);

        when(mockDataService.fetchHistoricalData()).thenReturn(new AsyncResult<>(emptyList()));

        service.updateStore();

        verify(mockDataService, times(1)).fetchHistoricalData();
        verify(mockRepository, times(1)).save(emptyList());
    }

    @Test
    public void getEuroToEuroExchangeRateIsOne() {
        final LocalDate date = LocalDate.parse("2016-08-03");

        final EcbExchangeRateService service = new EcbExchangeRateService(mockDataService, mockRepository);

        final ExchangeRate rate = service.getExchangeRate(EUR, date);

        assertThat(rate, is(notNullValue()));
        assertThat(rate.getConversionRate(), is(1.0));

        verify(mockDataService, times(0)).fetchHistoricalData();
        verify(mockDataService, times(0)).fetchLatestData();
    }

}