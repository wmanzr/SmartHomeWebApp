package RUT.smart_home.service;


import RUT.smart_home_contract.api.dto.CommandRequest;
import RUT.smart_home_contract.api.dto.CommandResponse;
import RUT.smart_home_contract.api.dto.PagedResponse;

public interface CommandService {

    PagedResponse<CommandResponse> getAllCommands(Long deviceId, int page, int size);

    CommandResponse getById(Long id);

    CommandResponse create(CommandRequest request);
}