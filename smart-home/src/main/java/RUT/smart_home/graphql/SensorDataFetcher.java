package RUT.smart_home.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import RUT.smart_home.service.SensorService;
import RUT.smart_home.service.SensorReadingService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.dto.SensorReadingResponse;
import RUT.smart_home_contract.api.dto.SensorResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@DgsComponent
public class SensorDataFetcher {
    private final SensorService sensorService;

    public SensorDataFetcher(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @DgsQuery
    public List<SensorResponse> sensors() {
        return sensorService.getAll();
    }

    @DgsQuery
    public SensorResponse sensorById(@InputArgument Long id) {
        return sensorService.getById(id);
    }

    @DgsMutation
    @Transactional
    public SensorResponse createSensor(@InputArgument("input") Map<String, Object> input) {
        SensorRequest request = new SensorRequest(
                (String) input.get("name"),
                SensorType.valueOf(((String)input.get("type")).toUpperCase()),
                (String) input.get("location")
        );
        return sensorService.create(request);
    }

    @DgsMutation
    @Transactional
    public SensorResponse updateSensor(@InputArgument Long id, @InputArgument("input") Map<String, String> input) {
        UpdateSensorRequest request = new UpdateSensorRequest(
                input.get("name"),
                input.get("location")
        );
        return sensorService.update(id, request);
    }

    @DgsMutation
    @Transactional
    public Long deleteSensor(@InputArgument Long id) {
        sensorService.delete(id);
        return id;
    }
}