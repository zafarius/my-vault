package vault.domain.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionTest {
    @Test
    public void testEntityAlreadyExists() {
        assertThatThrownBy(() -> {
            throw new EntityAlreadyExistsException("Test Exception.", ExceptionTest.class);
        })
        .isInstanceOf(EntityAlreadyExistsException.class)
        .message()
        .isEqualTo("Test Exception. Class: " + ExceptionTest.class.getName());
    }

    @Test
    public void testForbidden() {

        assertThatThrownBy(() -> {
            throw new ForbiddenException("Forbidden Exception");
        })
        .isInstanceOf(ForbiddenException.class)
        .message()
        .isEqualTo("Forbidden Exception");
    }
}
