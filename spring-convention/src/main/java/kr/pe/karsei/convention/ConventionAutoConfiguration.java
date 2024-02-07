package kr.pe.karsei.convention;

import kr.pe.karsei.convention.config.ConventionProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan
public class ConventionAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(ConventionProperties.class)
    public ConventionProperties conventionProperties() {
        return new ConventionProperties();
    }
}
