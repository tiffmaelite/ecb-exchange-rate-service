package grenier.tiffany.app.exchangerate.service.store;

import com.google.common.collect.ImmutableList;
import grenier.tiffany.app.exchangerate.model.ExchangeRate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Currency;

import static grenier.tiffany.app.exchangerate.service.EuroExchangeRateService.EUR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EuroExchangeRateRepositoryTest {

    private static final EuroExchangeRateRepository FILLED_STORE = new EuroExchangeRateRepository();
    private static final LocalDate date_1 = LocalDate.parse("2017-04-01");
    private static final LocalDate date_2 = LocalDate.parse("2017-04-02");
    private static final Currency chf = Currency.getInstance("CHF");
    private static final ExchangeRate chfRate_2 = new ExchangeRate(EUR, chf, date_2, 1.079);
    private static final ExchangeRate chfRate_1 = new ExchangeRate(EUR, chf, date_1, 1.07);

    @BeforeClass
    public static void setup() {
        FILLED_STORE.save(ImmutableList.of(
                chfRate_1,
                chfRate_2
        ));
    }

    @Test
    public void isEmptyOnInit() {
        final EuroExchangeRateRepository repository = new EuroExchangeRateRepository();
        assertTrue(repository.isEmpty());
    }

    @Test
    public void isEmptyAfterLoad() {
        assertFalse(FILLED_STORE.isEmpty());
    }

    @Test
    public void getWithoutDate() {
        final ExchangeRate latestChfRate = FILLED_STORE.get(chf);
        assertThat(latestChfRate, is(chfRate_2));
    }

    @Test
    public void get() {
        final ExchangeRate latestChfRate = FILLED_STORE.get(chf, date_1);
        assertThat(latestChfRate, is(chfRate_1));
    }

}