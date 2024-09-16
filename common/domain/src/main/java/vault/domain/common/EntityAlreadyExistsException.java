package vault.domain.common;

public final class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(final String message, final Class<?> entity) {
        super(message + " Class: " + entity.getName());
    }
}
