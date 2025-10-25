package RUT.smart_home.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_reading", schema = "public")
public class SensorReading extends BaseEntity {
    private Sensor sensor;
    private Double value;
    private String unit;
    private LocalDateTime timestamp;

    protected SensorReading() {}

    public SensorReading(Sensor sensor, Double value, String unit, LocalDateTime timestamp) {
        this.sensor = sensor;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    public Sensor getSensor() {
        return sensor;
    }

    @Column(name = "value", nullable = false)
    public Double getValue() {
        return value;
    }

    @Column(name = "unit", nullable = false)
    public String getUnit() {
        return unit;
    }

    @Column(name = "timestamp", nullable = false)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}