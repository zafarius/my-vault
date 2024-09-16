package vault.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vault.VaultApplication;
import org.springframework.test.web.servlet.MockMvc;
import vault.account.model.RequestAccountDTO;
import vault.domain.account.Account;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = VaultApplication.class
)
@AutoConfigureMockMvc
public class AccountEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenCreateAccount_thenStatus201() throws Exception {
        // setup
        val username = "user12";
        val password = "password123";
        val requestAccountDTO = new RequestAccountDTO(username, password, List.of(vault.account.model.AccountRoles.USER));

        // then
        mockMvc.perform(
                        post("/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAccountDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist()
                );
    }

    @Test
    public void whenCreateAccountDuplicate_thenStatus406() throws Exception {
        // setup
        val username = "user1";
        val requestAccountDTO1 = new RequestAccountDTO(username, "password123", List.of(vault.account.model.AccountRoles.USER));
        val requestAccountDTO2 = new RequestAccountDTO(username, "password321", List.of(vault.account.model.AccountRoles.USER));

        // then
        mockMvc.perform(
                        post("/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAccountDTO1))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist()
                );

        mockMvc.perform(
                        post("/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAccountDTO2))
                )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(
                        MockMvcResultMatchers.content()
                        .string("Account user1 already exists. Class: " + Account.class.getName())
                );
    }
}
