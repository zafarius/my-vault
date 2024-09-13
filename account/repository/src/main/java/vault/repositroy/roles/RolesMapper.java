package vault.repositroy.roles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vault.domain.roles.Roles;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RolesMapper {
    RolesEntity map(Roles role);
    @Mapping(target = "accounts", ignore = true)
    Roles map(RolesEntity rolesEntity);
}
