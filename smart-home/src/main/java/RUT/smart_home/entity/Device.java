package RUT.smart_home.entity;


import RUT.smart_home_contract.api.dto.DeviceStatus;
import RUT.smart_home_contract.api.dto.DeviceType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "device", schema = "public")
public class Device extends BaseEntity {
    private String name;
    private DeviceType type;
    private String location;
    private String metadata;
    private DeviceStatus status;

    protected Device() {}

    public Device(String name, DeviceType type, String location, String metadata, DeviceStatus status) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.metadata = metadata;
        this.status = status;
    }

    private List<Command> commands = new ArrayList<>();

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public DeviceType getType() {
        return type;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    @Column(name = "metadata")
    public String getMetadata() {
        return metadata;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public DeviceStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }
}