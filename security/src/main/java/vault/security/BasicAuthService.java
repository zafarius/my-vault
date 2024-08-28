package vault.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import vault.repositroy.account.AccountEntity;
import vault.repositroy.account.AccountRepository;
import vault.repositroy.roles.RolesEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class BasicAuthService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountEntity> account = accountRepository.findByUsername(username);
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