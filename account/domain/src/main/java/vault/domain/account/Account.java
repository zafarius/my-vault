package vault.domain.account;

import lombok.Data;
import vault.domain.roles.Roles;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Account {
    private UUID id;
    private final String username;
    private final String password;
    private final Set<Roles> accountRoles = new HashSet<>();
}
