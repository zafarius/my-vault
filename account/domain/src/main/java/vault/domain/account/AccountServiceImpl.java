package vault.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vault.domain.common.EntityAlreadyExistsException;
import vault.domain.common.SecurityRoles;
import vault.domain.roles.Roles;
import java.util.Optional;
import java.util.Set;
import lombok.val;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Optional<Account> findByUsernameIgnoreCase(final String username) {
        return accountRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public Account createAccount(final Account account) {

        if (findByUsernameIgnoreCase(account.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Account %s already exists.", account.getUsername()), Account.class);
        }

        addDefaultRoles(account.getAccountRoles());
        return accountRepository.save(account);
    }

    private void addDefaultRoles(final Set<Roles> accountRoles) {
        val roleUser = new Roles(SecurityRoles.USER);
        accountRoles.add(roleUser);
    }
}
