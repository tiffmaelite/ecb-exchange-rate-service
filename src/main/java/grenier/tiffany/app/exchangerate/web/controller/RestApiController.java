package grenier.tiffany.app.exchangerate.web.controller;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.service.EuroExchangeRateService;
import grenier.tiffany.app.exchangerate.web.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Currency;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.time.LocalDate.now;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping("/api")
public final class RestApiController {

    private final EuroExchangeRateService store;
    private final InputValidator validator;

    @Autowired
    private RestApiController(final EuroExchangeRateService store,
                              final InputValidator validator) {
        this.store = store;
        this.validator = validator;
    }

    @RequestMapping(value = "/getDummyConversionRate", method = GET)
    public ExchangeRate convertDummy() {
        return convert("EUR", now());
    }

    @RequestMapping(value = "/getConversionRate", method = GET)
    public ExchangeRate convert(@RequestParam("currency") final String currency)
            throws IllegalArgumentException {
        return convert(currency, null);
    }

    @RequestMapping(value = "/getPastConversionRate", method = GET)
    public ExchangeRate convert(@RequestParam("currency") final String currency,
                                @DateTimeFormat(iso = DATE) @RequestParam("date") final LocalDate date)
            throws IllegalArgumentException {
        checkArgument(!isNullOrEmpty(currency), "currency cannot be a null or empty string");
        final Currency validatedCurrency = validator.validateCurrency(currency);
        if (date == null) {
            return store.getExchangeRate(validatedCurrency);
        } else {
            final LocalDate validatedDate = validator.validateDate(date);
            return store.getExchangeRate(validatedCurrency, validatedDate);
        }
    }
}
