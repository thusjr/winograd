package winograd.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import winograd.backend.mapper.UserMapper;
import winograd.backend.model.User;
import winograd.backend.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    /**
     * 设置(name="userMapper")
     * 使其与userMapper这个bean有相同的名字，
     * 防止出现nouniquebeandefinitionexception
     */
    @MockBean(name="userMapper") // 
    private UserMapper uMapper;

    @Autowired
    private UserService uService;

    @Test
    public void testCreateUser(){
        User userOne = new User();
        userOne.setName("userName");
        userOne.setPassword("userPassword");
        userOne.setEmail("userEmail");
        Mockito.when(uMapper.createUser(userOne)).thenReturn(1);

        User userTwo = new User();
        userTwo.setName("userName");
        userTwo.setPassword("userPassword");
        userTwo.setEmail("userEmail");
        Mockito.when(uMapper.createUser(userTwo)).thenReturn(0);

        boolean result = uService.createUser(userOne);
        assertTrue(result);
        result = uService.createUser(userTwo);
        assertFalse(result);
    }

    @Test
    public void testUserExist(){
        Mockito.when(uMapper.testUserExist("existName")).thenReturn(true);
        Mockito.when(uMapper.testUserExist("noneExistName")).thenReturn(false);
        assertTrue(uService.testUserExist("existName"));
        assertFalse(uService.testUserExist("noneExistName"));
    }

    @Test
    public void testGetPassword(){
        User user = new User();
        user.setName("userName");
        user.setPassword("userPassword");
        user.setEmail("userEmail");
        Mockito.when(uMapper.getPassword(user.getName())).thenReturn(user.getPassword());
        Mockito.when(uMapper.getPassword("anotherName")).thenReturn(null);
        assertEquals(uService.getPassword(user.getName()), user.getPassword());
        assertNull(uService.getPassword("anotherName"));
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setName("userName");
        user.setPassword("userPassword");
        user.setEmail("userEmail");
        Mockito.when(uMapper.updateUser(user)).thenReturn(1);
        assertEquals(uService.updateUser(user), true);
        user.setName("anotherUserName");
        Mockito.when(uMapper.updateUser(user)).thenReturn(0);
        assertEquals(uService.updateUser(user), false);
    }
}