package grenier.tiffany.app.exchangerate.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EuroExchangeRateServiceConfiguration {

    @Primary
    @Bean
    @ConditionalOnProperty(name = "mock", havingValue = "false", matchIfMissing = true)
    public EuroExchangeRateService ecbEuroExchangeRateService() {
        return new EcbExchangeRateService();
    }

    @Bean
    @ConditionalOnProperty(name = "mock", havingValue = "true")
    public EuroExchangeRateService mockEuroExchangeRateService() {
        return new DummyEuroExchangeRateService();
    }
}
