package grenier.tiffany.app.exchangerate.adapter;

import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import grenier.tiffany.app.exchangerate.model.ecb.DatedEuroExchangeRates;
import grenier.tiffany.app.exchangerate.model.ecb.EcbReferenceRates;
import grenier.tiffany.app.exchangerate.model.ecb.EuroExchangeRate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.stream.Stream;

import static grenier.tiffany.app.exchangerate.service.EuroExchangeRateService.EUR;
import static java.util.stream.Collectors.toList;

public final class EcbAdapter {

    public Collection<ExchangeRate> adapt(final EcbReferenceRates ecbRates) {
        return ecbRates
                .getEuroExchangeRateTimeSeries()
                .getCube().stream()
                .flatMap(this::adapt)
                .collect(toList());
    }

    Stream<ExchangeRate> adapt(final DatedEuroExchangeRates c) {
        return c.getCube().stream()
                .map(r -> adapt(LocalDate.parse(c.getTime()), r));
    }

    ExchangeRate adapt(final LocalDate date, final EuroExchangeRate r) {
        return new ExchangeRate(EUR, Currency.getInstance(r.getCurrency()), date, r.getRate());
    }
}
