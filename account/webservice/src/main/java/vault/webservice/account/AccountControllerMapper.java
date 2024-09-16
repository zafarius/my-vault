package vault.webservice.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vault.domain.account.Account;
import vault.account.model.RequestAccountDTO;
import vault.account.model.ResponseAccountDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountControllerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountRoles", ignore = true)
    Account map(RequestAccountDTO requestAccountDTO);

    ResponseAccountDTO map(Account account);
}
