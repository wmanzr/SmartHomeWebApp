package RUT.smart_home_contract.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@Relation(collectionRelation = "sensors", itemRelation = "sensor")
public class SensorResponse extends RepresentationModel<SensorResponse> {
    private Long id;
    private String name;
    private SensorType type;
    private String location;

    protected SensorResponse() {}

    public SensorResponse(Long id, String name, SensorType type, String location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public SensorType getType() { return type; }
    public void setType(SensorType type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SensorResponse that = (SensorResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(type, that.type) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, type, location);
    }
}