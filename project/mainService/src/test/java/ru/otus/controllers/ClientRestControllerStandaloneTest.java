package ru.otus.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.config.WebConfig;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Document;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.crm.service.KladrServiceClientImpl;
import ru.otus.json.dataprocessor.ProcessorImpl;
import ru.otus.json.model.Result;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(value = {DbServiceClientImpl.class, ClientController.class})
@SpringBootTest
@AutoConfigureMockMvc
//@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
class ClientRestControllerStandaloneTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private DBServiceClient dbServiceClient;
    private ClientController clientController = new ClientController(dbServiceClient);

    @DisplayName("Проверка грида списка клиентов")
    @Test
    void testGetListClient() throws Exception {

        ProcessorImpl jsonProcessor = new ProcessorImpl();
        KladrServiceClientImpl kladrServiceClient = new KladrServiceClientImpl();
        Address address = new Address(142701, "Ленина", "1", "123", 1L);
        Phone phone = new Phone("123456789", 1L);

        String file = "test@example.com,test@example.net,john.doe@example.org";
        byte[] byts = file.getBytes("utf-8");
        MultipartFile mockFile = new MockMultipartFile("mockFile", "test.csv", "text/csv", byts);

        List<Result> result = jsonProcessor.getListResult(kladrServiceClient.getAddresesByZip("https://kladr-api.ru/api.php?contentType=building&withParent=1&zip=163000"));

        given(dbServiceClient.saveClient(result, "Test1", "123456789", "684ab73c-5881-40bd-876a-85cd7e5d1486", new MultipartFile[]{mockFile})).
                willReturn(new RedirectView("/", true));

        mvc.perform(get("/client/list").accept("application/json; charset=utf-8"))
                .andExpect(status().isOk());
    }

    @DisplayName("Проверка Kladr")
    @Test
    void testKladr() throws Exception {

        mvc.perform(
                        get("/client/kladr")
                                .param("zip", "163000")
                                .param("street", "")
                                .param("house", ""))
                .andExpect(status()
                        .isOk())
                .andDo(print());
    }

    @DisplayName("Проверка сохранения Клиента")
    @Test
    void testClientSave() throws Exception {

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.mvc.perform(get("/client/kladr")
                .param("zip", "163000")
                .param("street", "")
                .param("house", "")
        ).andExpect(status().isOk());

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        this.mvc.perform(multipart("/client/save")
                        .file(file)
                        .param("name", "Test1")
                        .param("number", "123456789")
                        .param("guid", "684ab73c-5881-40bd-876a-85cd7e5d1486"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl(null))
                .andDo(print());
    }

    @DisplayName("Проверка загрузки файла")
    @Test
    void testDownload2() throws Exception {

        given(dbServiceClient.loadDocument(1L)).willReturn(new Document(1L, "1.txt", "qwerty".getBytes(), "hash512 was not generated", "hash256 was not generated", 1L));

        mvc.perform(
                        get("/client/file/{id}", 1L)
                                .param("id", "1L")
                ).andExpect(status().isOk())
                .andExpect(content().string("qwerty"))
                .andExpect(content().contentType("application/octet-stream"))
                .andDo(print());
    }
}