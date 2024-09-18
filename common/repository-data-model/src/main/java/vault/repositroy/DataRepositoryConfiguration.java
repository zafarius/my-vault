package vault.repositroy;

import lombok.val;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Configure spring repository setup and init flyway migrations.
 */
@AutoConfigureBefore(FlywayAutoConfiguration.class)
@Configuration
@EntityScan
@EnableJpaRepositories
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan
public class DataRepositoryConfiguration {
    @Bean(initMethod = "migrate")
    @FlywayDataSource
    public Flyway flywayData(final DataSource dataSource) throws SQLException {
        try (val con = dataSource.getConnection()) {
            val vendor = con.getMetaData().getDatabaseProductName().toLowerCase();
            return new Flyway(
                    new FluentConfiguration()
                            .createSchemas(true)
                            .schemas("vault")
                            .defaultSchema("vault")
                            .locations(String.format("classpath:db/migration/%s", vendor))
                            .table("schema_version")
                            .installedBy("Vault")
                            .failOnMissingLocations(true)
                            .validateMigrationNaming(true)
                            .dataSource(dataSource));
        }
    }
}
