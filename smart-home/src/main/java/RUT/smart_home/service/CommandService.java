package RUT.smart_home.service;


import RUT.smart_home_contract.api.dto.CommandRequest;
import RUT.smart_home_contract.api.dto.CommandResponse;
import RUT.smart_home_contract.api.dto.PagedResponse;
import RUT.smart_home_contract.api.dto.UpdateCommandStatusRequest;

public interface CommandService {

    PagedResponse<CommandResponse> getAll(Long deviceId, int page, int size);

    CommandResponse getById(Long id);

    CommandResponse create(CommandRequest request);

    void updateStatus(Long id, UpdateCommandStatusRequest request);
}