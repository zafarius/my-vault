package vault.repository.account;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import vault.domain.account.Account;
import vault.domain.account.AccountRepository;
import vault.domain.common.SecurityRoles;
import vault.domain.roles.Roles;
import vault.repositroy.account.AccountRepositoryConfiguration;
import lombok.val;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@EnableAutoConfiguration
@DataJpaTest
@ContextConfiguration(classes = AccountRepositoryConfiguration.class)
@TestPropertySource(
        properties = {
                "spring.jpa.hibernate.ddl-auto=validate",
                "spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl",
                "spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl",
                "spring.jpa.properties.hibernate.format_sql=true",
                "spring.jpa.properties.hibernate.show_sql=true",
                "spring.jpa.properties.hibernate.globally_quoted_identifiers=true"
        })
public class AccountRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateAccountAndFound() {
        // setup
        val role1 = new Roles(SecurityRoles.USER);
        val role2 = new Roles(SecurityRoles.ADMIN);
        val password = "superSecurePassword";
        val passwordEncoded = "$2a$12$nSwMArKJZY.rnTLdYQS00OABLwN6u6wefqbP6W1jHP0RlSrTn9lBS";
        val account = new Account("User1", password);
        account.getAccountRoles().add(role1);
        account.getAccountRoles().add(role2);

        // when
        when(passwordEncoder.encode(password)).thenReturn(passwordEncoded);

        accountRepository.save(account);
        entityManager.flush();

        // then
        val createdAccount = accountRepository.findByUsername(account.getUsername());
        assertThat(createdAccount.isPresent()).isTrue();
        assertThat(createdAccount.get().getAccountRoles()).isEqualTo(account.getAccountRoles());
        assertThat(createdAccount.get().getAccountRoles()).isEqualTo(account.getAccountRoles());
        assertThat(createdAccount.get().getUsername()).isEqualTo(account.getUsername());
        assertThat(createdAccount.get().getPassword()).isEqualTo(passwordEncoded);
    }

    @Test
    public void testCreateAccountDuplicateThrowsException() {
        // setup
        val role1 = new Roles(SecurityRoles.USER);
        val role2 = new Roles(SecurityRoles.ADMIN);
        val password = "superSecurePassword";
        val account1 = new Account("UserA", password);
        account1.getAccountRoles().add(role1);
        account1.getAccountRoles().add(role2);
        val account2 = new Account("UserA", password);
        account2.getAccountRoles().add(role1);


        // when
        when(passwordEncoder.encode(password)).thenReturn("$2a$12$nSwMArKJZY.rnTLdYQS00OABLwN6u6wefqbP6W1jHP0RlSrTn9lBS");

        accountRepository.save(account1);
        entityManager.flush();
        accountRepository.save(account2);

        // then
        assertThatThrownBy(() -> {
            entityManager.flush();
        }).isInstanceOf(ConstraintViolationException.class);

    }

    @Test
    public void testAccountNotFound() {
        assertThat(accountRepository.findByUsername("IdoNotExist").isPresent()).isFalse();
    }
}
