package vault.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithSecurityContext;
import vault.domain.common.SecurityRoles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockVaultUserSecurityContextFactory.class)
public @interface WithMockVaultUser {

    String username() default "vault123";

    String password() default "pass123";

    String accountId() default "54f3f8b1-d9e0-4e9d-8049-f48d5d3b932e";

    Set<GrantedAuthority> AUTHORITIES = Set.of(new SimpleGrantedAuthority(SecurityRoles.USER));
}
