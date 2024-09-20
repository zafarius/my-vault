package vault.webservice.file;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import vault.security.SecurityConfiguration;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@ComponentScan(basePackageClasses = {
        FileControllerConfiguration.class,
        SecurityConfiguration.class
})
public class FileControllerConfiguration {
}
