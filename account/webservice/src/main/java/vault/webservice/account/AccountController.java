package vault.webservice.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import vault.account.model.RequestAccountDTO;
import vault.account.model.ResponseAccountDTO;
import vault.domain.account.AccountService;
import vault.domain.common.SecurityRoles;
import vault.webservice.contracts.account.AccountsApi;
import lombok.val;

@Controller
@RequiredArgsConstructor
public class AccountController implements AccountsApi {
    private final AccountService accountService;
    private final AccountControllerMapper accountControllerMapper;

    @Override
    public ResponseEntity<Void> createAccount(final RequestAccountDTO requestAccountDTO) {
        accountService.createAccount(accountControllerMapper.map(requestAccountDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @Secured(SecurityRoles.USER)
    public ResponseEntity<ResponseAccountDTO> getAccount() {
        val userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val account = accountService.findByUsernameIgnoreCase(userDetails.getUsername());
        return account.map((account1 ->
                        ResponseEntity
                                .status(HttpStatus.OK)
                                .body(accountControllerMapper.map(account1))
                ))
                .orElseThrow(() -> new RuntimeException(
                        String.format("Failed to retrieve Account: %s", userDetails.getUsername()))
                );
    }
}
