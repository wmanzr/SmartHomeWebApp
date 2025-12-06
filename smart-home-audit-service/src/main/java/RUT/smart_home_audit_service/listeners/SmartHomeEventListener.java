package RUT.smart_home_audit_service.listeners;

import RUT.smart_home_events_contract.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SmartHomeEventListener {
    private static final Logger log = LoggerFactory.getLogger(SmartHomeEventListener.class);
    private final Set<Long> processedCommandCreations = ConcurrentHashMap.newKeySet();
    private final Set<Long> processedDeviceCreations = ConcurrentHashMap.newKeySet();
    private final Set<Long> processedSensorReadingCreations = ConcurrentHashMap.newKeySet();
    private final Set<Long> processedSensorCreations = ConcurrentHashMap.newKeySet();

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "device-created-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "device.created"
            )
    )
    public void handleDeviceCreated(@Payload DeviceCreatedEvent event,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            if (!processedDeviceCreations.add(event.deviceId())) {
                log.warn("Duplicate event received for deviceId: {}", event.deviceId());
                channel.basicAck(deliveryTag, false);
                return;
            }
            log.info("Получено событие создания устройства: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "device-status-updated-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "device.status.updated"
            )
    )
    public void handleDeviceStatusUpdated(@Payload DeviceStatusUpdatedEvent event,
                                          Channel channel,
                                          @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("Получено событие обновления статуса устройства: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "device-deleted-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "device.deleted"
            )
    )
    public void handleDeviceDeleted(@Payload DeviceDeletedEvent event,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("Получено событие удаления устройства: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "sensor-created-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "sensor.created"
            )
    )
    public void handleSensorCreated(@Payload SensorCreatedEvent event,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            if (!processedSensorCreations.add(event.sensorId())) {
                log.warn("Duplicate event received for sensorId: {}", event.sensorId());
                channel.basicAck(deliveryTag, false);
                return;
            }
            log.info("Получено событие создания датчика: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "sensor-updated-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "sensor.updated"
            )
    )
    public void handleSensorUpdated(@Payload SensorUpdatedEvent event,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("Получено событие обновления датчика: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "sensor-deleted-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "sensor.deleted"
            )
    )
    public void handleSensorDeleted(@Payload SensorDeletedEvent event,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("Получено событие удаления датчика: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "sensor-reading-created-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "sensor.reading.created"
            )
    )
    public void handleSensorReadingCreated(@Payload SensorReadingCreatedEvent event,
                                           Channel channel,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            if (!processedSensorReadingCreations.add(event.readingId())) {
                log.warn("Duplicate event received for sensorReadingId: {}", event.readingId());
                channel.basicAck(deliveryTag, false);
                return;
            }
            log.info("Получено событие создания показания датчика: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "command-created-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "command.created"
            )
    )
    public void handleCommandCreated(@Payload CommandCreatedEvent event,
                                     Channel channel,
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            if (!processedCommandCreations.add(event.commandId())) {
                log.warn("Duplicate event received for commandId: {}", event.commandId());
                channel.basicAck(deliveryTag, false);
                return;
            }
            log.info("Получено событие создания команды: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = "command-status-updated-queue",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = "smart-home-exchange", type = "topic", durable = "true"),
                    key = "command.status.updated"
            )
    )
    public void handleCommandStatusUpdated(@Payload CommandStatusUpdatedEvent event,
                                           Channel channel,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("Получено событие обновления статуса команды: {}", event);
            // TODO
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Ошибка при обработке события: {}. Отправляем в DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "notification-queue.dlq", durable = "true"),
                    exchange = @Exchange(name = "dlx-exchange", type = "topic", durable = "true"),
                    key = "dlq.notifications"
            )
    )
    public void handleDlqMessages(Object failedMessage) {
        log.error("!!! Received message in DLQ: {}", failedMessage);
        // TODO
    }
}