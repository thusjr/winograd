package winograd.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import winograd.backend.mapper.UserMapper;
import winograd.backend.model.User;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserMapperTest {
    @Autowired
    private UserMapper uMapper;

    @Test
    public void testMapper(){
        User user = new User();
        user.setName("userName");
        user.setPassword("userPassword");
        user.setEmail("userEmail");
        assertEquals(uMapper.testUserExist(user.getName()), false);
        assertEquals(uMapper.createUser(user), 1);
        assertEquals(uMapper.testUserExist(user.getName()), true);
        assertEquals(uMapper.testUserExist("noneExist"), false);
        assertEquals(uMapper.getPassword(user.getName()), "userPassword");
        user.setPassword("newPassword");
        assertEquals(uMapper.updateUser(user), 1);
        assertEquals(uMapper.getPassword(user.getName()), "newPassword");
    }
}