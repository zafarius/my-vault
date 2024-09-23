package vault.repository.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.val;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vault.domain.account.Account;
import vault.domain.account.AccountRepository;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountRepositoryJpa accountRepositoryJpa;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Account save(final Account account) {
        val accountEntity = encodePassword(accountMapper.map(account));
        return accountMapper.map(accountRepositoryJpa.save(accountEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Account> findByUsernameIgnoreCase(final String username) {
        return accountRepositoryJpa.findByUsernameIgnoreCase(username).map(accountMapper::map);
    }

    private AccountEntity encodePassword(final AccountEntity accountEntity) {
        accountEntity.setPassword(
                passwordEncoder.encode(accountEntity.getPassword())
        );

        return accountEntity;
    }

}
