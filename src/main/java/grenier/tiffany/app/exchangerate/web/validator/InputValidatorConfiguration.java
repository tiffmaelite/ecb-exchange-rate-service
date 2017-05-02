package grenier.tiffany.app.exchangerate.web.validator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class InputValidatorConfiguration {

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
