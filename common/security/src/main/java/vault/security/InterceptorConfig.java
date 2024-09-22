package vault.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final AccountInterceptor accountInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry
                .addInterceptor(accountInterceptor)
                .addPathPatterns("/account/**/file");
    }
}
