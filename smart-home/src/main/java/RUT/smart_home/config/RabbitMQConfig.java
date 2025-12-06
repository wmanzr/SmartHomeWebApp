package RUT.smart_home.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "smart-home-exchange";

    public static final String ROUTING_KEY_DEVICE_CREATED = "device.created";
    public static final String ROUTING_KEY_DEVICE_STATUS_UPDATED = "device.status.updated";
    public static final String ROUTING_KEY_DEVICE_DELETED = "device.deleted";
    public static final String ROUTING_KEY_SENSOR_CREATED = "sensor.created";
    public static final String ROUTING_KEY_SENSOR_UPDATED = "sensor.updated";
    public static final String ROUTING_KEY_SENSOR_DELETED = "sensor.deleted";
    public static final String ROUTING_KEY_SENSOR_READING_CREATED = "sensor.reading.created";
    public static final String ROUTING_KEY_COMMAND_CREATED = "command.created";
    public static final String ROUTING_KEY_COMMAND_STATUS_UPDATED = "command.status.updated";
    public static final String FANOUT_EXCHANGE = "analytics-fanout";

    @Bean
    public TopicExchange smartHomeExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange analyticsExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper().findAndRegisterModules());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("NACK: Message delivery failed! " + cause);
            }
        });
        return rabbitTemplate;
    }
}