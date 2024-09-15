package vault.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vault.domain.common.EntityAlreadyExists;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Optional<Account> findByUsername(final String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account createAccount(final Account account) {

        if (findByUsername(account.getUsername()).isPresent()) {
            throw new EntityAlreadyExists(String.format("Account %s already exists.", account.getUsername()), Account.class);
        }

        return accountRepository.save(account);
    }
}
