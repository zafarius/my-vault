package vault.repository.roles;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import vault.domain.common.SecurityRoles;
import vault.domain.roles.Roles;
import vault.repositroy.roles.RolesEntity;
import vault.repositroy.roles.RolesMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RolesMapperTest {

    @InjectMocks
    private RolesMapperImpl rolesMapper;

    @Test
    public void testMapRolesToRolesEntity() {
        // setup
        val role = new Roles(SecurityRoles.USER);

        // when
        val roleEntity = rolesMapper.map(role);

        // then
        assertThat(roleEntity.getRoleName()).isEqualTo(role.getRoleName());
        assertThat(roleEntity.getAccounts()).isNull();
    }

    @Test
    public void testMapRolesEntityToRoles() {
        // setup
        val roleEntity = new RolesEntity();
        roleEntity.setRoleName(SecurityRoles.ADMIN);

        // when
        val role = rolesMapper.map(roleEntity);

        // then
        assertThat(role.getRoleName()).isEqualTo(roleEntity.getRoleName());
        assertThat(role.getAccounts()).isNull();
    }

}
