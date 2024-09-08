package vault.webservice.account;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import vault.webservice.account.AccountsApi;

@Controller
public class AccountController implements AccountsApi {
    @Override
    public ResponseEntity<Void> createAccounts() {
        return null;
    }
}
