package vault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vault.repositroy.account.AccountRepository;
import vault.security.Roles;

@Controller
public class SampleController {

    @Secured(Roles.ADMIN)
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.status(HttpStatus.OK).body("MOIN INDEX");
    }

    @GetMapping("/api/test")
    @Secured(Roles.USER)
    public ResponseEntity<String> testt() {
        return ResponseEntity.status(HttpStatus.OK).body("MOIN TEST");
    }
}
