package RUT.smart_home_audit_service.listeners;

import RUT.smart_home_events_contract.events.CallCommandEventFromSensorReading;
import RUT.smart_home_events_contract.events.CommandStatusUpdatedEvent;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CallCommandListener {
    private static final Logger log = LoggerFactory.getLogger(SmartHomeEventListener.class);

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.audit.analytics", durable = "true"),
                    exchange = @Exchange(name = "analytics-fanout", type = "fanout")
            )
    )
    public void handleRating(@Payload CallCommandEventFromSensorReading event,
                             Channel channel,
                             @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("Уведомление: Получено показание с датчика. Требуется команда для устройства с ID {} с дейтсвием {}", event.deviceId(), event.commandAction());
    }
}