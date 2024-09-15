package vault.security.account;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vault.domain.account.Account;
import vault.domain.account.AccountRepository;
import vault.domain.common.SecurityRoles;
import vault.domain.roles.Roles;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BasicAuthServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private BasicAuthService basicAuthService;

    /**
     * Test loading account by username.
     */
    @Test
    void testLoadUserByUsername() {
        // setup
        val username = "sample";
        val roles = new Roles(SecurityRoles.USER);

        val account = new Account("Userius", "password123", Set.of(roles));

        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));

        // when
        val result = basicAuthService.loadUserByUsername(username);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(account.getUsername());
        assertThat(result.getPassword()).isEqualTo(account.getPassword());
        assertThat(result.getAuthorities().toString()).contains(roles.getRoleName());
    }

    /**
     * Negative test loading account by username.
     */
    @Test
    void testLoadUserByUsernameNotExists() {
        // setup
        val username = "sample";

        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when
        val result = basicAuthService.loadUserByUsername(username);

        // then
        assertThat(result).isNull();
    }
}
