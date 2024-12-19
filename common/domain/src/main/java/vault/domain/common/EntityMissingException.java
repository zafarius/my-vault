package vault.domain.common;

public final class EntityMissingException extends RuntimeException {
    public EntityMissingException(final String message) {
        super(message);
    }
}
