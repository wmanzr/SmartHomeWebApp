package RUT.smart_home_audit_service.listeners;

import RUT.smart_home_events_contract.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SmartHomeEventListener {
    private static final Logger log = LoggerFactory.getLogger(SmartHomeEventListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device-created-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "device.created"
    ))
    public void handleDeviceCreated(DeviceCreatedEvent event) {
        log.info("Получено событие создания устройства: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device-status-updated-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "device.status.updated"
    ))
    public void handleDeviceStatusUpdated(DeviceStatusUpdatedEvent event) {
        log.info("Получено событие обновления статуса устройства: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device-deleted-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "device.deleted"
    ))
    public void handleDeviceDeleted(DeviceDeletedEvent event) {
        log.info("Получено событие удаления устройства: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sensor-created-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "sensor.created"
    ))
    public void handleSensorCreated(SensorCreatedEvent event) {
        log.info("Получено событие создания датчика: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sensor-updated-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "sensor.updated"
    ))
    public void handleSensorUpdated(SensorUpdatedEvent event) {
        log.info("Получено событие обновления датчика: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sensor-deleted-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "sensor.deleted"
    ))
    public void handleSensorDeleted(SensorDeletedEvent event) {
        log.info("Получено событие удаления датчика: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sensor-reading-created-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "sensor.reading.created"
    ))
    public void handleSensorReadingCreated(SensorReadingCreatedEvent event) {
        log.info("Получено событие создания показания датчика: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "command-created-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "command.created"
    ))
    public void handleCommandCreated(CommandCreatedEvent event) {
        log.info("Получено событие создания команды: {}", event);
        // TODO
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "command-status-updated-queue", durable = "true"),
            exchange = @Exchange(name = "smart-home-exchange", type = "topic"),
            key = "command.status.updated"
    ))
    public void handleCommandStatusUpdated(CommandStatusUpdatedEvent event) {
        log.info("Получено событие обновления статуса команды: {}", event);
        // TODO
    }
}