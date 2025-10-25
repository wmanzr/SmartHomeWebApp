package RUT.smart_home.entity;

import RUT.smart_home_contract.api.dto.SensorType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sensor", schema = "public")
public class Sensor extends BaseEntity {
    private String name;
    private SensorType type;
    private String location;

    protected Sensor() {}

    public Sensor(String name, SensorType type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    private List<SensorReading> readings = new ArrayList<>();

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<SensorReading> getReadings() {
        return readings;
    }

    public void setReadings(List<SensorReading> readings) {
        this.readings = readings;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public SensorType getType() {
        return type;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}