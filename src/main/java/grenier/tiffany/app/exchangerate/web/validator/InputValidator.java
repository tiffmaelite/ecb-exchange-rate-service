package grenier.tiffany.app.exchangerate.web.validator;

import java.time.LocalDate;
import java.util.Currency;

public interface InputValidator {

    Currency validateCurrency(String currency) throws IllegalArgumentException;

    LocalDate validateDate(LocalDate date) throws IllegalArgumentException;
}
