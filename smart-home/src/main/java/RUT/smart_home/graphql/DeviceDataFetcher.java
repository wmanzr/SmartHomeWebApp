package RUT.smart_home.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import RUT.smart_home.service.DeviceService;
import RUT.smart_home_contract.api.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@DgsComponent
public class DeviceDataFetcher {
    private final DeviceService deviceService;

    public DeviceDataFetcher(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @DgsQuery
    public List<DeviceResponse> devices() {
        return deviceService.getAll();
    }

    @DgsQuery
    public DeviceResponse deviceById(@InputArgument Long id) {
        return deviceService.getById(id);
    }

    @DgsMutation
    @Transactional
    public DeviceResponse createDevice(@InputArgument("input") Map<String, Object> input) {
        DeviceRequest request = new DeviceRequest(
                (String) input.get("name"),
                DeviceType.valueOf(((String)input.get("type")).toUpperCase()),
                (String) input.get("location"),
                (String) input.getOrDefault("metadata", null)
        );
        return deviceService.create(request);
    }

    @DgsMutation
    @Transactional
    public DeviceResponse updateDeviceStatus(@InputArgument Long id, @InputArgument("input") Map<String, Object> input) {
        UpdateDeviceStatusRequest request = new UpdateDeviceStatusRequest(
                DeviceStatus.valueOf(((String) input.get("status")).toUpperCase()),
                (String) input.getOrDefault("metadata", null)
        );
        return deviceService.updateStatus(id, request);
    }

    @DgsMutation
    @Transactional
    public Long deleteDevice(@InputArgument Long id) {
        deviceService.delete(id);
        return id;
    }
}