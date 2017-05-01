package grenier.tiffany.app.exchangerate.model;


import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public final class ExchangeRate {

    private final Currency currencyFrom;
    private final Currency currencyTo;
    private final LocalDate conversionDate;
    private final double conversionRate;

    public ExchangeRate(final Currency currencyFrom,
                        final Currency currencyTo,
                        final LocalDate conversionDate,
                        final double conversionRate) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.conversionDate = conversionDate;
        this.conversionRate = conversionRate;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public LocalDate getConversionDate() {
        return conversionDate;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "currencyFrom=" + currencyFrom +
                ", currencyTo=" + currencyTo +
                ", conversionDate=" + conversionDate +
                ", conversionRate=" + conversionRate +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExchangeRate exchangeRate = (ExchangeRate) o;
        return Double.compare(exchangeRate.conversionRate, conversionRate) == 0 &&
                Objects.equals(currencyFrom, exchangeRate.currencyFrom) &&
                Objects.equals(currencyTo, exchangeRate.currencyTo) &&
                Objects.equals(conversionDate, exchangeRate.conversionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyFrom, currencyTo, conversionDate, conversionRate);
    }
}
