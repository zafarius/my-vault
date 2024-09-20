package vault.webservice.account;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vault.domain.account.Account;
import vault.domain.account.AccountService;
import vault.account.model.RequestAccountDTO;
import lombok.val;
import vault.domain.common.SecurityRoles;
import vault.domain.roles.Roles;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = AccountControllerConfiguration.class)
@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc
@ImportAutoConfiguration(AopAutoConfiguration.class)

public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountControllerMapper accountControllerMapper;

    @Test
    void whenCreateAccount_ThenStatus201() throws Exception {
        // setup
        val username = "user1";
        val password = "passwordSuper";
        val requestAccountDTO = new RequestAccountDTO(username, password);
        val account = new Account(username, password);
        account.getAccountRoles().add(new Roles(SecurityRoles.USER));

        // when
        when(accountControllerMapper.map(requestAccountDTO)).thenReturn(account);
        when(accountService.createAccount(account)).thenReturn(account);

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
    @WithMockUser(username = "user1", authorities = SecurityRoles.USER)
    void whenGetAccountExists_ThenStatus200() throws Exception {
        // setup
        val username = "user1";
        val password = "passwordSuper";
        val account = new Account(username, password);
        account.getAccountRoles().add(new Roles(SecurityRoles.USER));

        account.setId(UUID.randomUUID());
        val responseAccountDTO = new vault.account.model.ResponseAccountDTO(account.getId(), account.getUsername());

        // when
        when(accountControllerMapper.map(account)).thenReturn(responseAccountDTO);
        when(accountService.findByUsername(account.getUsername())).thenReturn(Optional.of(account));

        // then
        mockMvc.perform(
                        get("/account")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(account.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(account.getUsername())
                );
    }

    @Test
    void whenCreateAccountMalFormedUserName_ThenStatus400() throws Exception {
        // setup
        val username = "user";
        val password = "password123";
        val requestAccountDTO = new RequestAccountDTO(username, password);
        val account = new Account(username, password);
        account.getAccountRoles().add(new Roles(SecurityRoles.USER));


        // when
        when(accountControllerMapper.map(requestAccountDTO)).thenReturn(account);
        when(accountService.createAccount(account)).thenReturn(account);

        // then
        mockMvc.perform(
                        post("/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAccountDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist()
                );
    }

    @Test
    void whenCreateAccountMalFormedPassword_ThenStatus400() throws Exception {
        // setup
        val username = "user1";
        val password = "pass";
        val requestAccountDTO = new RequestAccountDTO(username, password);
        val account = new Account(username, password);
        account.getAccountRoles().add(new Roles(SecurityRoles.USER));


        // when
        when(accountControllerMapper.map(requestAccountDTO)).thenReturn(account);
        when(accountService.createAccount(account)).thenReturn(account);

        // then
        mockMvc.perform(
                        post("/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAccountDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist()
                );
    }
}
