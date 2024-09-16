package vault.domain.roles;

import lombok.Data;
import vault.domain.account.Account;

import java.util.List;

@Data
public class Roles {
    private final String roleName;
    private List<Account> accounts;
}
