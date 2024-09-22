
package vault.repository.file;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;
import vault.domain.file.FileRepository;
import lombok.val;
import vault.domain.file.VaultFile;
import vault.repository.DataRepositoryConfiguration;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@EnableAutoConfiguration
@DataJpaTest
@ContextConfiguration(classes = DataRepositoryConfiguration.class)
@TestPropertySource(
        properties = {
                "files.output_dir=build",
                "spring.jpa.hibernate.ddl-auto=validate",
                "spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl",
                "spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl",
                "spring.jpa.properties.hibernate.format_sql=true",
                "spring.jpa.properties.hibernate.show_sql=true",
                "spring.jpa.properties.hibernate.globally_quoted_identifiers=true"
        })
public class FileRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void testSaveAndGetFile() throws Exception {
        // setup
        val accountId = UUID.fromString("4b085d51-b364-41b5-a4e2-c25dac3b7a4a");
        val fileName = "sample";
        val fileType = "PNG";
        val testFile = ResourceUtils.getFile("classpath:sample.txt");
        val testFileSize = Files.size(testFile.toPath());

        // when
        try (val contentStream = new FileInputStream(testFile)) {
            val file = new VaultFile(
                    fileName,
                    fileType,
                    testFileSize,
                    contentStream
            );
            fileRepository.save(accountId, file);
            entityManager.flush();
        }

        // then
        try (val contentStream = new FileInputStream(testFile)) {
            val result = fileRepository.findByAccountId(accountId).get(0);
            assertThat(result.getContentStream().readAllBytes()).isEqualTo(contentStream.readAllBytes());
            assertThat(result.getType()).isEqualTo(fileType);
            assertThat(result.getName()).isEqualTo(fileName);
            assertThat(result.getSize()).isEqualTo(testFileSize);
        }
    }
}
