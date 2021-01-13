package winograd.backend.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class User implements Serializable{
    private static final long serialVersionUID = -7529822069190157358L;
    private String name;
    private String password;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String userPassword) {
        this.password = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String userEmail) {
        this.email = userEmail;
    }

}