package grenier.tiffany.app.exchangerate.service;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;

import java.time.LocalDate;
import java.util.Currency;

public interface EuroExchangeRateService {

    Currency EUR = Currency.getInstance("EUR");

    /**
     * returns foreign exchange rate from Euro to provided currency on given date
     */
    ExchangeRate getExchangeRate(Currency currency, LocalDate date);
}
