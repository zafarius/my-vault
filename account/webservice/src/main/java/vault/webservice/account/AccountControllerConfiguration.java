package vault.webservice.account;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import vault.security.SecurityConfiguration;

@Configuration
@ComponentScan(basePackageClasses = {
        AccountControllerConfiguration.class,
        SecurityConfiguration.class
})
public class AccountControllerConfiguration {
}
