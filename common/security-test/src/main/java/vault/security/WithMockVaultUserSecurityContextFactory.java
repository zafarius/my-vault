package vault.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import vault.domain.common.VaultUser;
import lombok.val;
import java.util.UUID;

public class WithMockVaultUserSecurityContextFactory implements WithSecurityContextFactory<WithMockVaultUser> {
    @Override
    public SecurityContext createSecurityContext(final WithMockVaultUser vaultUser) {
        val context = SecurityContextHolder.createEmptyContext();
        val principal = VaultUser.
                builder()
                .accountId(UUID.fromString(vaultUser.accountId()))
                .username(vaultUser.username())
                .password(vaultUser.password())
                .authorities(vaultUser.AUTHORITIES)
                .build();
        val auth = UsernamePasswordAuthenticationToken.authenticated(
                principal,
                principal.getPassword(),
                principal.getAuthorities()
        );
        context.setAuthentication(auth);
        return context;
    }
}
