package vault.webservice.account;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import vault.account.model.RequestAccountDTO;
import vault.domain.account.Account;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AccountControllerMapperTest {

    private final AccountControllerMapperImpl accountControllerMapper =
            new AccountControllerMapperImpl();

    @Test
    public void testMapRequestAccountDTOToAccount() {
        // setup
        val requestDTO = new RequestAccountDTO();
        requestDTO.setUsername("test1");
        requestDTO.setPassword("superSecured1");

        // when
        val account = accountControllerMapper.map(requestDTO);

        // then
        assertThat(requestDTO.getPassword()).isEqualTo(account.getPassword());
        assertThat(requestDTO.getUsername()).isEqualTo(account.getUsername());
    }

    @Test
    public void testMapAccountToRequestAccountDTO() {
        // setup
        val account = new Account("test11", "superSecure");

        // when
        val responseAccountDTO = accountControllerMapper.map(account);

        // then
        assertThat(account.getUsername()).isEqualTo(responseAccountDTO.getUsername());
    }
}
