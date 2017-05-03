package grenier.tiffany.app.exchangerate;

import grenier.tiffany.app.exchangerate.service.DummyEuroExchangeRateService;
import grenier.tiffany.app.exchangerate.service.EcbExchangeRateService;
import grenier.tiffany.app.exchangerate.service.EuroExchangeRateService;
import grenier.tiffany.app.exchangerate.service.fetch.DataService;
import grenier.tiffany.app.exchangerate.service.fetch.EcbFileDataService;
import grenier.tiffany.app.exchangerate.service.fetch.EcbRestDataService;
import grenier.tiffany.app.exchangerate.web.validator.DummyInputValidator;
import grenier.tiffany.app.exchangerate.web.validator.EcbInputValidator;
import grenier.tiffany.app.exchangerate.web.validator.InputValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {
    @Primary
    @Bean
    public DataService ecbFileService() {
        return new EcbFileDataService();
    }

    @Bean
    public DataService ecbRestService() {
        return new EcbRestDataService();
    }

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

    @Primary
    @Bean
    @ConditionalOnProperty(name = "mock", havingValue = "false", matchIfMissing = true)
    public InputValidator ecbInputValidator() {
        return new EcbInputValidator();
    }

    @Bean
    @ConditionalOnProperty(name = "mock", havingValue = "true")
    public InputValidator mockInputValidator() {
        return new DummyInputValidator();
    }
}
