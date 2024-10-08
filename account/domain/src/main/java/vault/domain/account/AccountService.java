package vault.domain.account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsernameIgnoreCase(String username);
    Account createAccount(Account account);
}
