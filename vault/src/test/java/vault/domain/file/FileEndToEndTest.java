package vault.domain.file;

import lombok.val;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vault.VaultApplication;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = VaultApplication.class
)
@AutoConfigureMockMvc
public class FileEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("userTest1")
    void whenCreateFiles_ThenStatus201() throws Exception {
        // setup
        val accountId = UUID.fromString("4b085d51-b364-41b5-a4e2-c25dac3b7a4a");
        val multipartFile1 = new MockMultipartFile(
                "files", "123",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes());

        // then
        mockMvc.perform(
                        multipart("/api/v1/account/{accountId}/file", accountId.toString())
                                .file(multipartFile1)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @WithUserDetails("userTest1")
    void whenCreateFilesWithInvalidAccountId_ThenStatus403() throws Exception {
        // setup
        val accountId = UUID.fromString("6d896416-6ccf-44de-810e-b122da62bd25");
        val multipartFile1 = new MockMultipartFile(
                "files", "123",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes());

        // then
        val result = mockMvc.perform(
                        multipart("/api/v1/account/{accountId}/file", accountId.toString())
                                .file(multipartFile1)
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();

        assertThat(
                Objects.requireNonNull(
                        result.getResolvedException()
                ).getMessage()
        ).isEqualTo(
                String.format("AccountId: %s is not valid.", accountId)
        );
    }

    @Test
    @WithUserDetails("userTest2")
    void whenCreateMultipleFilesAndGetFiles_ThenStatus200() throws Exception {
        // setup
        val accountId = UUID.fromString("4b085d51-b364-41b5-a4e2-c25dac3b7a4b");
        val multipartFile1 = new MockMultipartFile(
                "files", "file1",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe1".getBytes());

        val multipartFile2 = new MockMultipartFile(
                "files", "file2",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe2".getBytes());

        val multipartFile3 = new MockMultipartFile(
                "files", "file3",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe3".getBytes());

        mockMvc.perform(
                        multipart("/api/v1/account/{accountId}/file", accountId.toString())
                                .file(multipartFile1)
                                .file(multipartFile2)
                                .file(multipartFile3)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(""));

        //* REQUEST PAGE 1 *//
        val result = mockMvc.perform(
                        get("/api/v1/account/{accountId}/file", accountId.toString())
                                .param("pageSize", "2")
                                .param("pageNumber", "0")
                                .param("sortBy", "CREATED_DATE")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();

        // then
        val json = new JSONObject(result.getResponse().getContentAsString());

        // unzip
        val bytes = Base64.getDecoder().decode(json.get("content").toString());
        val zipInput = new ZipInputStream(new ByteArrayInputStream(bytes));
        val byteList = new ArrayList<byte[]>();
        //get first entry
        zipInput.getNextEntry();
        byteList.add(zipInput.readAllBytes());
        //get second entry
        zipInput.getNextEntry();
        byteList.add(zipInput.readAllBytes());

        val file1Bytes = multipartFile1.getBytes();
        val file2Bytes = multipartFile2.getBytes();
        val file3Bytes = multipartFile3.getBytes();

        val countMatches = byteList
                .stream()
                .filter(
                        (r) ->
                                Arrays.equals(r, file1Bytes)
                                        || Arrays.equals(r, file2Bytes)
                                        || Arrays.equals(r, file3Bytes)
                )
                .count();


        assertThat(countMatches).isEqualTo(2);
        assertThat(json.get("totalPages")).isEqualTo(2);
        assertThat(json.get("contentSize")).isEqualTo(2);

        //* REQUEST PAGE 2 *//
        val result2 = mockMvc.perform(
                        get("/api/v1/account/{accountId}/file", accountId.toString())
                                .param("pageSize", "2")
                                .param("pageNumber", "1")
                                .param("sortBy", "CREATED_DATE")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();

        // then
        val json2 = new JSONObject(result2.getResponse().getContentAsString());

        // unzip
        val bytes2 = Base64.getDecoder().decode(json2.get("content").toString());
        val zipInput2 = new ZipInputStream(new ByteArrayInputStream(bytes2));
        val byteList2 = new ArrayList<byte[]>();

        //get first entry
        zipInput2.getNextEntry();
        byteList2.add(zipInput2.readAllBytes());

        assertThat((long) byteList2.size()).isEqualTo(1);
        assertThat(json2.get("totalPages")).isEqualTo(2);
        assertThat(json2.get("contentSize")).isEqualTo(1);
    }

    @Test
    @WithUserDetails("userTest2")
    void whenGetFilesNoResults_ThenStatus404() throws Exception {
        // setup
        val accountId = UUID.fromString("4b085d51-b364-41b5-a4e2-c25dac3b7a4b");

        mockMvc.perform(
                        get("/api/v1/account/{accountId}/file", accountId.toString())
                                .param("pageSize", "4")
                                .param("pageNumber", "5")
                                .param("sortBy", "CREATED_DATE")
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
