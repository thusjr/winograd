package winograd.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSONObject;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import winograd.backend.model.User;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeAll
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    public void loginTest() throws Exception {
        User user = new User();
        user.setName("userTest");
        user.setPassword("userTest");
        user.setEmail("userTest");
        String requstJson = JSONObject.toJSONString(user);
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(requstJson))
                .andExpect(status().isBadRequest());
        mvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(requstJson))
                .andExpect(status().isOk());
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(requstJson))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void registerTest() throws Exception {
        User user = new User();
        user.setName("userTest");
        user.setPassword("userTest");
        user.setEmail("userTest");
        String requstJson = JSONObject.toJSONString(user);
        mvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(requstJson))
                .andExpect(status().isOk());
        mvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(requstJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void updateTest() throws Exception {
        User user = new User();
        user.setName("userTest");
        user.setPassword("userTest");
        user.setEmail("userTest");
        String requstJson = JSONObject.toJSONString(user);
        mvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(requstJson))
                .andExpect(status().isOk());
        user.setPassword("passwordChanged");
        requstJson = JSONObject.toJSONString(user);
        mvc.perform(post("/user/update").contentType(MediaType.APPLICATION_JSON).content(requstJson))
				.andExpect(status().isOk());
		user.setName("nonexist");
        requstJson = JSONObject.toJSONString(user);
        mvc.perform(post("/user/update").contentType(MediaType.APPLICATION_JSON).content(requstJson))
				.andExpect(status().isBadRequest());
		user.setName("userTest");
        user.setPassword("userTest");
        requstJson = JSONObject.toJSONString(user);
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(requstJson))
                .andExpect(status().isBadRequest());
    }
}
