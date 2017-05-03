package grenier.tiffany.app.exchangerate.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class InvalidCurrencyException extends IllegalArgumentException {

    public InvalidCurrencyException(final String s) {
        super(s);
    }

    public InvalidCurrencyException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidCurrencyException(final Throwable cause) {
        super(cause);
    }
}
