package vault.webservice.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import vault.domain.account.Account;
import vault.account.model.RequestAccountDTO;
import vault.account.model.ResponseAccountDTO;
import vault.domain.roles.Roles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountControllerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "accountRoles", target = "accountRoles", qualifiedByName = "requestRolesToAccountRoles")
    Account map(RequestAccountDTO requestAccountDTO);

    ResponseAccountDTO map(Account account);

    @Named("requestRolesToAccountRoles")
    static Set<Roles> requestRolesToAccountRoles(List<vault.account.model.AccountRoles> requestRoles) {
        Set<Roles> accountRoles = new HashSet<>();
        for (vault.account.model.AccountRoles roles: requestRoles) {
            accountRoles.add(new Roles(roles.name()));
        }

        return accountRoles;
    }
}
