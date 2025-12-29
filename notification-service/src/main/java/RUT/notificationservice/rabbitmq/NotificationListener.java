package RUT.notificationservice.rabbitmq;

import RUT.notificationservice.websocket.NotificationHandler;
import RUT.smart_home_events_contract.events.DeviceStatusUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);
    private final NotificationHandler notificationHandler;

    public NotificationListener(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.notifications.browser", durable = "true"),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
                    key = "device.status.updated"
            )
    )
    public void handleUserRatedEvent(DeviceStatusUpdatedEvent event) {
        log.info("Received event from RabbitMQ: {}", event);

        // Формируем сообщение для пользователя
        String userMessage = String.format(
                "{\"type\": \"device_status_changed\", \"deviceId\": %d, \"deviceName\": \"%s\", \"deviceType\": \"%s\", \"newStatus\": \"%s\", \"metadata\": %s, \"timestamp\": \"%s\"}",
                event.deviceId(),
                event.deviceName() != null ? event.deviceName() : "",
                event.deviceType() != null ? event.deviceType() : "",
                event.newStatus() != null ? event.newStatus() : "",
                event.metadata() != null ? "\"" + event.metadata() + "\"" : "null",
                System.currentTimeMillis()
        );

        // Отправляем в браузер
        notificationHandler.broadcast(userMessage);
    }
}