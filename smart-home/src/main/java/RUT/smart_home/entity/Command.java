package RUT.smart_home.entity;

import RUT.smart_home_contract.api.dto.CommandAction;
import RUT.smart_home_contract.api.dto.CommandStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "command", schema = "public")
public class Command extends BaseEntity {
    private Device device;
    private CommandAction action;
    private String value;
    private CommandStatus status;
    private String message;

    protected Command() {}

    public Command(Device device, CommandAction action, String value, CommandStatus status, String message) {
        this.device = device;
        this.action = action;
        this.value = value;
        this.status = status;
        this.message = message;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    public Device getDevice() {
        return device;
    }

    @Column(name = "action", nullable = false)
    public CommandAction getAction() {
        return action;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public CommandStatus getStatus() {
        return status;
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setAction(CommandAction action) {
        this.action = action;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}