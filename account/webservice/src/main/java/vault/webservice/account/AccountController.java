package vault.webservice.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import vault.account.model.RequestAccountDTO;
import vault.domain.account.AccountService;
import vault.webservice.contracts.account.AccountsApi;


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
}
