package RUT.smart_home.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public TopicExchange smartHomeExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
}