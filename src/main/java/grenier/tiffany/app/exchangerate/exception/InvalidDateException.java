package grenier.tiffany.app.exchangerate.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class InvalidDateException extends IllegalArgumentException {

    public InvalidDateException(final String s) {
        super(s);
    }

    public InvalidDateException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidDateException(final Throwable cause) {
        super(cause);
    }
}
