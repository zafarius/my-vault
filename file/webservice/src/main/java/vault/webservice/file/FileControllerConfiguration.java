package vault.webservice.file;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        FileControllerConfiguration.class
})
public class FileControllerConfiguration {
}
