package vault.domain.account;

import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findByUsernameIgnoreCase(String username);
}
