package vault.webservice.account;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

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
