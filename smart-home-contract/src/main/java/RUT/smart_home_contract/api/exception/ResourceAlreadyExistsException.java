package RUT.smart_home_contract.api.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resource, Object id) {
        super(String.format("%s уже существует: %s", resource, id));
    }
}