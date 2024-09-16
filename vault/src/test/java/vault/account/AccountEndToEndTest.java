package vault.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vault.VaultApplication;
import org.springframework.test.web.servlet.MockMvc;
import vault.account.model.RequestAccountDTO;
import vault.domain.account.Account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        val requestAccountDTO = new RequestAccountDTO(username, password);

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
    @WithMockUser(username = "user123", password = "password123")
    public void whenGetAccount_thenStatus200() throws Exception {
        // setup
        val username = "user123";
        val password = "password123";
        val requestAccountDTO = new RequestAccountDTO(username, password);

        // then
        mockMvc.perform(
                        post("/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAccountDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist()
                );

        mockMvc.perform(
                        get("/account")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists());
    }

    @Test
    public void whenCreateAccountDuplicate_thenStatus406() throws Exception {
        // setup
        val username = "user1";
        val requestAccountDTO1 = new RequestAccountDTO(username, "password123");
        val requestAccountDTO2 = new RequestAccountDTO(username, "password321");

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
