package vault.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import lombok.val;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vault.domain.common.ForbiddenException;
import vault.domain.common.VaultUser;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountInterceptorTest {

    @InjectMocks
    private AccountInterceptor accountInterceptor;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private final UUID accountId = UUID.fromString("5a97c255-2dce-429b-9887-23fc65fb5b91");

    @BeforeEach
    public void before() {
        val vaultUser = VaultUser
                .builder()
                .accountId(accountId)
                .build();
        SecurityContextHolder.clearContext();
        when(authentication.getPrincipal()).thenReturn(vaultUser);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testAccountIdExists() {
        // setup
        val mockRequest = new MockHttpServletRequest();
        val mockResponse = new MockHttpServletResponse();
        mockRequest.setRequestURI(
                String.format("/account/%s/file", accountId)
                );

        // then
        assertThatNoException().isThrownBy(() ->
                accountInterceptor.preHandle(mockRequest, mockResponse, new Object())
        );

        verify(authentication).getPrincipal();
        verify(securityContext).getAuthentication();
    }

    @Test
    public void testAccountIdInvalid() {
        // setup
        val mockRequest = new MockHttpServletRequest();
        val mockResponse = new MockHttpServletResponse();
        val invalidAccountId = UUID.fromString("b3a2d19c-3feb-4c66-aec7-90e17b9dc502");

        mockRequest.setRequestURI(
                String.format("/account/%s/file", invalidAccountId)
        );

        // then
        assertThatThrownBy(() ->
                accountInterceptor.preHandle(mockRequest, mockResponse, new Object())
        )
        .isInstanceOf(ForbiddenException.class)
        .message()
        .isEqualTo(String.format(
        "AccountId: %s is not valid.", invalidAccountId
        ));

        verify(authentication).getPrincipal();
        verify(securityContext).getAuthentication();
    }
}
