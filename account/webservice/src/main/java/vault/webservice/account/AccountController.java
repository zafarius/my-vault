package vault.webservice.account;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import vault.webservice.contracts.account.AccountsApi;

@Controller
public class AccountController implements AccountsApi {
    /**
     * Create new accounts otherwise return error.
     *
     * @return @{@link ResponseEntity}
     */
    @Override
    public ResponseEntity<Void> createAccounts() {
        return null;
    }
}
