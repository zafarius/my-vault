package vault.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vault.repositroy.account.AccountRepositoryJpa;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@Configuration
public class SecurityConfiguration {

    /**
     * Configuration of request handling.
     *
     * @param http
     * @return @{@link SecurityFilterChain}
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Basic authentication bean with jdbc.
     *
     * @param accountRepositoryJpa
     * @return @{@link UserDetailsService}
     */
    @Bean
    public UserDetailsService userDetailsService(final AccountRepositoryJpa accountRepositoryJpa) {
        return new BasicAuthService(accountRepositoryJpa);
    }


    /**
     * Password Encoder used by authentication flow.
     *
     * @return @{@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
