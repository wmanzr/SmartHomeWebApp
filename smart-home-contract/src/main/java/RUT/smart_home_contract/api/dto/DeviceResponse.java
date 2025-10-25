package RUT.smart_home_contract.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@Relation(collectionRelation = "devices", itemRelation = "device")
public class DeviceResponse extends RepresentationModel<DeviceResponse> {
    private Long id;
    private String name;
    private DeviceType type;
    private String location;
    private String metadata;
    private DeviceStatus status;
    protected DeviceResponse() {}
    public DeviceResponse(Long id, String name, DeviceType type, String location, String metadata, DeviceStatus status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.metadata = metadata;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public DeviceType getType() { return type; }
    public void setType(DeviceType type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }

    public DeviceStatus getStatus() { return status; }
    public void setStatus(DeviceStatus status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DeviceResponse that = (DeviceResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(type, that.type) && Objects.equals(location, that.location)
                && Objects.equals(metadata, that.metadata) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, type, location, metadata, status);
    }
}