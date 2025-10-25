package RUT.smart_home.graphql;

import RUT.smart_home_contract.api.dto.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import RUT.smart_home.service.SensorReadingService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@DgsComponent
public class SensorReadingDataFetcher {
    private final SensorReadingService readingService;

    public SensorReadingDataFetcher(SensorReadingService readingService) {
        this.readingService = readingService;
    }

    @DgsData(parentType = "SensorReading", field = "sensor")
    public SensorResponse sensor(DataFetchingEnvironment dfe) {
        SensorReadingResponse reading = dfe.getSource();
        return reading.getSensor();
    }

    @DgsQuery
    public PagedResponse<SensorReadingResponse> readings(
            @InputArgument Long sensorId,
            @InputArgument int page,
            @InputArgument int size) {
        return readingService.getAll(sensorId, page, size);
    }

    @DgsQuery
    public SensorReadingResponse readingById(@InputArgument Long id) {
        return readingService.getById(id);
    }

    @DgsMutation
    @Transactional
    public SensorReadingResponse createReading(@InputArgument("input") Map<String, Object> input) {
        SensorReadingRequest request = new SensorReadingRequest(
                Long.parseLong(input.get("sensorId").toString()),
                Double.parseDouble(input.get("value").toString()),
                (String) input.get("unit"),
                LocalDateTime.parse((String) input.get("timestamp"))
        );
        return readingService.create(request);
    }
}