package vault.repositroy.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vault.domain.account.Account;
import vault.repositroy.roles.RolesMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RolesMapper.class}
)
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    AccountEntity map(Account account);

    Account map(AccountEntity accountEntity);
}
