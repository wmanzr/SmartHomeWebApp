package RUT.smart_home_contract.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@Relation(collectionRelation = "commands", itemRelation = "command")
public class CommandResponse extends RepresentationModel<CommandResponse> {
    private Long id;
    private DeviceResponse device;
    private CommandAction action;
    private String value;
    private CommandStatus status;
    private String message;
    protected  CommandResponse() {}
    public CommandResponse(Long id, DeviceResponse device, CommandAction action, String value, CommandStatus status, String message) {
        this.id = id;
        this.device = device;
        this.action = action;
        this.value = value;
        this.status = status;
        this.message = message;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DeviceResponse getDevice() { return device; }
    public void setDevice(DeviceResponse device) { this.device = device; }

    public CommandAction getAction() { return action; }
    public void setAction(CommandAction action) { this.action = action; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public CommandStatus getStatus() { return status; }
    public void setStatus(CommandStatus status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommandResponse that = (CommandResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(device, that.device) && Objects.equals(action, that.action)
                && Objects.equals(value, that.value) && Objects.equals(status, that.status)  && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, device, action, value, status, message);
    }
}