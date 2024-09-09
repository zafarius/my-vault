package vault.security;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vault.repositroy.account.AccountEntity;
import vault.repositroy.account.AccountRepositoryJpa;
import lombok.val;
import vault.repositroy.roles.RolesEntity;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class BasicAuthServiceTest {

    @Mock
    private AccountRepositoryJpa accountRepositoryJpa;

    @InjectMocks
    private BasicAuthService basicAuthService;

    /**
     * Test loading account by username.
     */
    @Test
    void testLoadUserByUsername() {
        // setup
        val username = "sample";
        val roles = new RolesEntity();
        roles.setRoleName(Roles.USER.replace("ROLE_", ""));

        val accountEntity = new AccountEntity();
        accountEntity.setUsername(username);
        accountEntity.setPassword("password");
        accountEntity.setAccountRoles(Set.of(roles));

        Mockito.when(accountRepositoryJpa.findByUsername(username)).thenReturn(Optional.of(accountEntity));

        // when
        val result = basicAuthService.loadUserByUsername(username);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(accountEntity.getUsername());
        assertThat(result.getPassword()).isEqualTo(accountEntity.getPassword());
        assertThat(result.getAuthorities().toString()).contains(roles.getRoleName());
    }

    /**
     * Negative test loading account by username.
     */
    @Test
    void testLoadUserByUsernameNotExists() {
        // setup
        val username = "sample";

        Mockito.when(accountRepositoryJpa.findByUsername(username)).thenReturn(Optional.empty());

        // when
        val result = basicAuthService.loadUserByUsername(username);

        // then
        assertThat(result).isNull();
    }
}
