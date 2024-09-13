package vault.domain.account;

import lombok.Data;
import vault.domain.roles.Roles;

import java.util.Set;

@Data
public class Account {
    private final String username;
    private final String password;
    private final Set<Roles> accountRoles;
}
