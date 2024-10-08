
package vault.webservice.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vault.domain.file.FileService;
import lombok.val;
import vault.security.WithMockVaultUser;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@ContextConfiguration(classes = FileControllerConfiguration.class)
@WebMvcTest(controllers = FileController.class)
@AutoConfigureMockMvc
@ImportAutoConfiguration(AopAutoConfiguration.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    @WithMockVaultUser(accountId = "bab1406f-798f-495d-b2f6-d7c25597bfae")
    void whenCreateFiles_ThenStatus201() throws Exception {
        // setup
        val accountId = UUID.fromString("bab1406f-798f-495d-b2f6-d7c25597bfae");
        val multipartFile1 = new MockMultipartFile(
                "files",
                "123",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes()
        );

        // then
        mockMvc.perform(
                multipart("/api/v1/account/{accountId}/file", accountId.toString())
                        .file(multipartFile1)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(""));


        verify(fileService).uploadFile(eq(accountId), any());
    }

    @Test
    @WithMockVaultUser(accountId = "bab1406f-798f-495d-b2f6-d7c25597bfae")
    void whenGetFiles_ThenStatus200() throws Exception {
        // setup
        val response = "Hallo".getBytes();
        val accountId = UUID.fromString("bab1406f-798f-495d-b2f6-d7c25597bfae");

        // when
        when(fileService.getZippedContent(accountId)).thenReturn(response);

        // then
        mockMvc.perform(
                        get("/api/v1/account/{accountId}/file", accountId.toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(response))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"download\""))
                .andExpect(MockMvcResultMatchers.content().contentType("application/zip"));


        verify(fileService).getZippedContent(accountId);
    }
}
