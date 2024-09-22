
package vault.domain.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class VaultUser implements UserDetails {
    private final UUID accountId;
    private final String password;
    private final String username;
    private final Set<GrantedAuthority> authorities;
}

