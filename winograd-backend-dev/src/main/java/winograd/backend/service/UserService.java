package winograd.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import winograd.backend.mapper.UserMapper;
import winograd.backend.model.User;

@Service
public class UserService {
    @Autowired
    private UserMapper dao;

    public boolean createUser(User user){
        return dao.createUser(user) > 0;
    }

    public boolean testUserExist(String name){
        return dao.testUserExist(name);
    }

    public String getPassword(String name){
        return dao.getPassword(name);
    }

    public User getUser(String name){
        return dao.getUser(name);
    }

    public boolean updateUser(User user){
        return dao.updateUser(user) > 0;
    }
}