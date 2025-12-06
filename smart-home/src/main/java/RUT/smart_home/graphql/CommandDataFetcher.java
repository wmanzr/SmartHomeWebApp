package RUT.smart_home.graphql;

import com.netflix.graphql.dgs.*;
import RUT.smart_home.service.CommandService;
import RUT.smart_home_contract.api.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@DgsComponent
public class CommandDataFetcher {

    private final CommandService commandService;

    public CommandDataFetcher(CommandService commandService) {
        this.commandService = commandService;
    }

    @DgsData(parentType = "Command", field = "device")
    public DeviceResponse device(DgsDataFetchingEnvironment dfe) {
        CommandResponse command = dfe.getSource();
        return command.getDevice();
    }

    @DgsQuery
    public RUT.smart_home_contract.api.dto.PagedResponse<CommandResponse> commands(
            @InputArgument Long deviceId,
            @InputArgument int page,
            @InputArgument int size) {
        return commandService.getAll(deviceId, page, size);
    }

    @DgsQuery
    public CommandResponse commandById(@InputArgument Long id) {
        return commandService.getById(id);
    }

    @DgsMutation
    @Transactional
    public CommandResponse createCommand(@InputArgument("input") Map<String, Object> input) {
        CommandRequest req = new CommandRequest(
                Long.parseLong(input.get("deviceId").toString()),
                CommandAction.valueOf(((String) input.get("action")).toUpperCase()),
                (String) input.getOrDefault("value", null)
        );
        return commandService.create(req);
    }
}