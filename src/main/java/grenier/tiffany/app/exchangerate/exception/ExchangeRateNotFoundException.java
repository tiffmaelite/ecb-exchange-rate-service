package grenier.tiffany.app.exchangerate.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class ExchangeRateNotFoundException extends NoSuchElementException {

    public ExchangeRateNotFoundException(final String s) {
        super(s);
    }
}
