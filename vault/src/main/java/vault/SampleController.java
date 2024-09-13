package vault;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vault.security.SecurityRoles;

@Controller
public class SampleController {

    /**
     * Sample.
     *
     * @param request
     * @return @{@link ResponseEntity}
     */
    @Secured(SecurityRoles.ADMIN)
    @GetMapping("/")
    public ResponseEntity<String> index(final RequestEntity request) {
        return ResponseEntity.status(HttpStatus.OK).body("MOIN INDEX");
    }

    /**
     * Sample.
     *
     * @return @{@link ResponseEntity}
     */
    @GetMapping("/api/test")
    @Secured(SecurityRoles.USER)
    public ResponseEntity<String> testt() {
        return ResponseEntity.status(HttpStatus.OK).body("MOIN TEST");
    }
}
