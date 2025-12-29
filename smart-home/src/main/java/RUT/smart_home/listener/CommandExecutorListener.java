package RUT.smart_home.listener;

import RUT.smart_home.config.RabbitMQConfig;
import RUT.smart_home.service.CommandExecutionService;
import RUT.smart_home_events_contract.events.CommandCreatedEvent;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutorListener {
    private final CommandExecutionService commandExecutionService;

    public CommandExecutorListener(CommandExecutionService commandExecutionService) {
        this.commandExecutionService = commandExecutionService;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.smart-home.command.executor", durable = "true"),
                    exchange = @Exchange(name = RabbitMQConfig.EXCHANGE_NAME, type = "topic"),
                    key = RabbitMQConfig.ROUTING_KEY_COMMAND_CREATED
            )
    )
    public void executeCommand(CommandCreatedEvent event) {
        System.out.println(" Executing command: " +
                "commandId=" + event.commandId() +
                ", deviceId=" + event.deviceId() +
                ", action=" + event.action() +
                ", value=" + event.value() +
                ", status=" + event.status() + " FROM COMMAND_EXECUTOR");

        try {
            commandExecutionService.executeCommand(event.commandId());
            System.out.println(" Command executed successfully.");
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }
}