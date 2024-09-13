package vault.repositroy.account;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import vault.domain.account.Account;
import vault.repositroy.roles.RolesMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RolesMapper.class}
)
public interface AccountMapper {
    AccountEntity map(Account account);
    Account map(AccountEntity accountEntity);
}
