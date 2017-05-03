package grenier.tiffany.app.exchangerate.web.controller;

import grenier.tiffany.app.exchangerate.exception.ExchangeRateNotFoundException;
import grenier.tiffany.app.exchangerate.exception.InvalidCurrencyException;
import grenier.tiffany.app.exchangerate.exception.InvalidDateException;
import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.service.EuroExchangeRateService;
import grenier.tiffany.app.exchangerate.web.ApiError;
import grenier.tiffany.app.exchangerate.web.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Currency;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.time.LocalDate.now;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Available endpoints:
 * {@code /api/getDummyConversionRate}
 * {@code /api/getConversionRate?currency=<currency-code>}
 * {@code /api/getPastConversionRate?currency=<currency-code>&date=<yyy-mm-dd>}
 *
 * TODO: add endpoint for retrieving latest date + add endpoint for retrieving all available currencies for a given date + use parametrized URLS
 */
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

    @ExceptionHandler({InvalidDateException.class, InvalidCurrencyException.class})
    public ResponseEntity<ApiError> handleBadRequest(final HttpServletRequest request,
                                                     final Exception e) {
        ApiError error = new ApiError(e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler({ExchangeRateNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(final HttpServletRequest request,
                                                   final Exception e) {
        ApiError error = new ApiError(e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @RequestMapping(value = "/getDummyConversionRate", method = GET)
    public ExchangeRate convertDummy() {
        return convert("EUR", now());
    }

    @RequestMapping(value = "/getConversionRate", method = GET)
    public ExchangeRate convert(@RequestParam("currency") final String currency) {
        checkArgument(!isNullOrEmpty(currency), "currency cannot be a null or empty string");
        final Currency validatedCurrency = validator.validateCurrency(currency);
        return store.getLatestExchangeRate(validatedCurrency);
    }

    @RequestMapping(value = "/getPastConversionRate", method = GET)
    public ExchangeRate convert(@RequestParam("currency") final String currency,
                                @DateTimeFormat(iso = DATE) @RequestParam("date") final LocalDate date) {
        checkArgument(!isNullOrEmpty(currency), "currency cannot be a null or empty string");
        final Currency validatedCurrency = validator.validateCurrency(currency);
        final LocalDate validatedDate = validator.validateDate(date);
        return store.getExchangeRate(validatedCurrency, validatedDate);
    }
}
