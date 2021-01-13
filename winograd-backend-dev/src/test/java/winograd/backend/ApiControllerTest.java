package winograd.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import winograd.lucene.service.IndexManager;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiControllerTest {
    MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeAll
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    private IndexManager iManager = new IndexManager();

    @Test
    public void searchTest() throws Exception {
        iManager.createIndex(100, 1, 0, "data_test",false); 
        try {
            mvc.perform(get("/api/search").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("entry", "中国")
                    .param("page", "1").param("datalocation", "data_test")).andExpect(status().isOk());
            mvc.perform(get("/api/search").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("entry", "bbbb")
                    .param("page", "1").param("datalocation", "data_test")).andExpect(status().isBadRequest());
            mvc.perform(get("/api/latest").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("type", "bj"))
                    .andExpect(status().isOk());
            mvc.perform(get("/api/latest").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("type", "xxxnone"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
