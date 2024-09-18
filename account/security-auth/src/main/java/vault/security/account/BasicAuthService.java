package vault.security.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import vault.domain.account.Account;
import vault.domain.account.AccountRepository;
import vault.domain.roles.Roles;

import java.util.Optional;

@RequiredArgsConstructor
public class BasicAuthService implements UserDetailsService {
    private final AccountRepository accountRepository;

    /**
     * Build {@link UserDetails} by finding user with {@link AccountRepository}.
     *
     * @param username
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.map(account1 ->
                User
                        .builder()
                        .username(account1.getUsername())
                        .password(account1.getPassword())
                        .authorities(account1.getAccountRoles().stream().map(Roles::getRoleName).toArray(String[]::new))
                        .build()
        ).orElse(null);
    }
}
