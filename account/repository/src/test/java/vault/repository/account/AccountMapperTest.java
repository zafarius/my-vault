package vault.repository.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vault.domain.account.Account;
import vault.domain.roles.Roles;
import vault.repositroy.account.AccountEntity;
import vault.repositroy.account.AccountMapperImpl;
import lombok.val;
import vault.repositroy.roles.RolesEntity;
import vault.repositroy.roles.RolesMapper;
import vault.security.SecurityRoles;
import static org.mockito.Mockito.verify;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {
    @Mock
    private RolesMapper rolesMapper;

    @InjectMocks
    private AccountMapperImpl accountMapper;

    @Test
    public void testMapAccountToAccountEntity() {
        // setup
        val role = new Roles(SecurityRoles.USER);
        val account = new Account("UserA", "random", Set.of(role));

        // when
        val accountEntity = accountMapper.map(account);

        // then
        assertThat(accountEntity.getUsername()).isEqualTo(account.getUsername());
        assertThat(accountEntity.getPassword()).isEqualTo(account.getPassword());
        verify(rolesMapper).map(role);
    }

    @Test
    public void testMapAccountEntityToAccount() {
        // setup
        val roleEntity = new RolesEntity();
        roleEntity.setRoleName(SecurityRoles.USER);
        val accountEntity = new AccountEntity();
        accountEntity.setId(UUID.randomUUID());
        accountEntity.setUsername("User1");
        accountEntity.setPassword("superSecuredPW");
        accountEntity.setCreatedDate(ZonedDateTime.now());
        accountEntity.setVersion(0);
        accountEntity.setAccountRoles(Set.of(roleEntity));

        // when
        val account = accountMapper.map(accountEntity);

        // then
        assertThat(account.getUsername()).isEqualTo(accountEntity.getUsername());
        assertThat(account.getPassword()).isEqualTo(accountEntity.getPassword());
        verify(rolesMapper).map(roleEntity);
    }
}
