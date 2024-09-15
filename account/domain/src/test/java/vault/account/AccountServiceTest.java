package vault.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vault.domain.account.Account;
import vault.domain.account.AccountRepository;
import vault.domain.account.AccountServiceImpl;
import lombok.val;
import vault.domain.common.EntityAlreadyExistsException;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private  AccountServiceImpl accountService;

    @Test
    public void testCreateAccount() {
        // setup
        val account = new Account("username1", "password", Set.of());

        // when
        when(accountRepository.findByUsername(account.getUsername())).thenReturn(Optional.empty());
        when(accountRepository.save(account)).thenReturn(account);

        val createdAccount = accountService.createAccount(account);

        // then
        assertThat(createdAccount).isEqualTo(account);
        verify(accountRepository).findByUsername(account.getUsername());
        verify(accountRepository).save(account);
    }

    @Test
    public void testAccountExists() {
        // setup
        val account = new Account("username1", "password", Set.of());

        // when
        when(accountRepository.findByUsername(account.getUsername())).thenReturn(Optional.of(account));

        // then
        assertThatThrownBy(
                () -> accountService.createAccount(account)
        ).isInstanceOf(EntityAlreadyExistsException.class)
                .message()
                .isEqualTo(
                        "Account username1 already exists. Class: " + Account.class.getName()
                );
    }
}
