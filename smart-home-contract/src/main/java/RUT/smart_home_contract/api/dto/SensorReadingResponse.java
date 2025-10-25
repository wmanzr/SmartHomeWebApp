package RUT.smart_home_contract.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.Objects;

@Relation(collectionRelation = "readings", itemRelation = "reading")
public class SensorReadingResponse extends RepresentationModel<SensorReadingResponse> {

    private Long id;
    private SensorResponse sensor;
    private Double value;
    private String unit;
    private LocalDateTime timestamp;
    protected SensorReadingResponse() {}
    public SensorReadingResponse(Long id, SensorResponse sensor, Double value, String unit, LocalDateTime timestamp) {
        this.id = id;
        this.sensor = sensor;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SensorResponse getSensor() { return sensor; }
    public void setSensor(SensorResponse sensor) { this.sensor = sensor; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SensorReadingResponse that = (SensorReadingResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(sensor, that.sensor)
                && Objects.equals(value, that.value) && Objects.equals(unit, that.unit)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, sensor, value, unit, timestamp);
    }
}