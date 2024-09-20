package vault.webservice.file;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import vault.security.SecurityConfiguration;

@Configuration
@ComponentScan(basePackageClasses = {
        FileControllerConfiguration.class,
        SecurityConfiguration.class
})
public class FileControllerConfiguration {
}
