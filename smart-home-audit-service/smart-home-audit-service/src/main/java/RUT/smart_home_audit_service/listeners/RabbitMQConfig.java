package RUT.smart_home_audit_service.listeners;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {
    @Bean
    public SimpleMessageConverter messageConverter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("RUT.smart_home_events_contract.events.*",
                "java.time.*"));
        return converter;
    }
}