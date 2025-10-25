package RUT.smart_home_contract.api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Object id) {
        super(String.format("%s с id=%s не найден", resource, id));
    }
}