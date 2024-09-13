package vault.security.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import vault.domain.account.AccountRepository;

@Configuration
public class AccountSecurityConfiguration {
    /**
     * Basic authentication bean with jdbc.
     *
     * @param accountRepository
     * @return @{@link UserDetailsService}
     */
    @Bean
    public UserDetailsService userDetailsService(final AccountRepository accountRepository) {
        return new BasicAuthService(accountRepository);
    }
}
