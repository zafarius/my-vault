package vault.repositroy.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vault.domain.roles.Roles;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RolesMapper {
    @Mapping(target = "accounts", ignore = true)
    RolesEntity map(Roles role);
    @Mapping(target = "accounts", ignore = true)
    Roles map(RolesEntity rolesEntity);
}
