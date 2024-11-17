package dmd.clientmanagement.exceptions;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(Long id) {
        super("Service with ID " + id + " not found.");
    }
}

