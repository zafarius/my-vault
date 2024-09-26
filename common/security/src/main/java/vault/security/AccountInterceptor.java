package vault.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import vault.domain.common.ForbiddenException;
import vault.domain.common.VaultUser;

@Component
public class AccountInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        verify(extractAccountId(request.getRequestURI()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @SuppressWarnings("MagicNumber")
    private String extractAccountId(final String requestURI) {
        val context = "account/";
        return requestURI
                .substring(requestURI.indexOf(context) + context.length())
                .substring(0, 36); //length of UUID
    }

    private void verify(final String accountId) {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        val vaultUser = (VaultUser) authentication.getPrincipal();
        val vaultUserId = vaultUser.getAccountId().toString();
        if (!accountId.equals(vaultUserId)) {
            throw new ForbiddenException(
                    String.format(
                            "AccountId: %s is not valid.", accountId
                    )
            );
        }
    }
}
