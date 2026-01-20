package RUT.smart_home.listener;

import RUT.smart_home.service.CommandService;
import RUT.smart_home_contract.api.dto.CommandAction;
import RUT.smart_home_contract.api.dto.CommandRequest;
import RUT.smart_home_events_contract.events.CallCommandEventFromSensorReading;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InternalAnalyticsListener {
    private final CommandService commandService;

    public InternalAnalyticsListener(CommandService commandService) {
        this.commandService = commandService;
    }
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.smart-home.analytics.log", durable = "true"),
                    exchange = @Exchange(name = "analytics-fanout", type = "fanout")
            )
    )
    public void createCommandByAnalytics(CallCommandEventFromSensorReading event) {
        System.out.println(" Required command with : " +
                "deviceId=" + event.deviceId() +
                ", sensorType=" + event.sensorType() +
                ", value=" + event.value() +
                ", commandAction=" + event.commandAction() +
                ", timestamp=" + event.timestamp() + " FROM SMART_HOME_REST");

        try {
            CommandRequest commandRequest = new CommandRequest(
                    event.deviceId(),
                    CommandAction.valueOf(event.commandAction()),
                    event.value() != null ? event.value() : null
            );

            commandService.create(commandRequest);

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid command action: " + event.commandAction());
        } catch (Exception e) {
            System.err.println("Error creating command from analytics: " + e.getMessage());
            e.printStackTrace();
        }
    }
}