package grenier.tiffany.app.exchangerate.web;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.service.EuroExchangeRateService;
import grenier.tiffany.app.exchangerate.web.controller.RestApiController;
import grenier.tiffany.app.exchangerate.web.validator.InputValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Currency;

import static grenier.tiffany.app.exchangerate.service.EuroExchangeRateService.EUR;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestApiController.class)
//TODO: use Spock?
public final class RestApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EuroExchangeRateService mockService;
    @MockBean
    private InputValidator mockValidator;

    @Test
    public void getPastConversionRateShouldReturnRateFromService() throws Exception {
        final String currencyCode = "GBP";
        final Currency currency = Currency.getInstance(currencyCode);

        final String dateString = "2016-08-03";
        final LocalDate date = LocalDate.parse(dateString);

        final double rate = 0.83705;

        when(mockService.getExchangeRate(currency, date)).thenReturn(new ExchangeRate(EUR, currency, date, rate));
        when(mockValidator.validateCurrency(currencyCode)).thenReturn(currency);
        when(mockValidator.validateDate(date)).thenReturn(date);

        mockMvc.perform(get("/api/getPastConversionRate?currency=" + currencyCode + "&date=" + dateString))
                .andExpect(status().isOk())
                .andExpect(content().json(//TODO: response file in resources
                        "{"
                                + "\"currencyFrom\" :\"EUR\","
                                + "\"currencyTo\" :\"" + currencyCode + "\","
                                + "\"conversionDate\" :\"" + dateString + "\","
                                + "\"conversionRate\" :" + rate
                                + "}"
                ));
    }

    @Test
    public void getConversionRateShouldReturnRateFromService() throws Exception {
        final String currencyCode = "GBP";
        final Currency currency = Currency.getInstance(currencyCode);

        final String latestDateString = "2017-04-28";
        final LocalDate latestDate = LocalDate.parse(latestDateString);

        final double rate = 0.83705;

        when(mockService.getLatestExchangeRate(currency)).thenReturn(new ExchangeRate(EUR, currency, latestDate, rate));
        when(mockValidator.validateCurrency(currencyCode)).thenReturn(currency);

        this.mockMvc.perform(get("/api/getConversionRate?currency=" + currencyCode))
                .andExpect(status().isOk())
                .andExpect(content().json(//TODO: response file in resources
                        "{"
                                + "\"currencyFrom\" :\"EUR\","
                                + "\"currencyTo\" :\"" + currencyCode + "\","
                                + "\"conversionDate\" :\"" + latestDateString + "\","
                                + "\"conversionRate\" :" + rate
                                + "}"
                ));
    }

}