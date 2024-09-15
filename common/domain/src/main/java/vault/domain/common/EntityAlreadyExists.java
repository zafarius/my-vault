package vault.domain.common;

public final class EntityAlreadyExists extends RuntimeException {
    public EntityAlreadyExists(final String message, final Class<?> entity) {
        super(message + " Class: " + entity.getName());
    }
}
