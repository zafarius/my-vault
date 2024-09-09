package vault.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import vault.repositroy.account.AccountEntity;
import vault.repositroy.account.AccountRepositoryJpa;
import vault.repositroy.roles.RolesEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class BasicAuthService implements UserDetailsService {
    private final AccountRepositoryJpa accountRepositoryJpa;

    /**
     * Build {@link UserDetails} by finding user with {@link AccountRepositoryJpa}.
     *
     * @param username
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<AccountEntity> account = accountRepositoryJpa.findByUsername(username);
        return account.map(accountEntity ->
                User
                        .builder()
                        .username(accountEntity.getUsername())
                        .password(accountEntity.getPassword())
                        .roles(accountEntity.getAccountRoles().stream().map(RolesEntity::getRoleName).toArray(String[]::new))
                        .build()
        ).orElse(null);
    }
}
