package winograd.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import winograd.backend.model.User;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(username, userpassword, useremail) VALUES(#{name},#{password},#{email})")
    int createUser(User user);

    @Select("SELECT (count(*) > 0) FROM user WHERE username = #{name}")
    boolean testUserExist(String name);

    @Select("SELECT userpassword FROM user WHERE username = #{name}")
    String getPassword(String name);

    @Select("SELECT username as name, userpassword as password, useremail as email FROM user WHERE username = #{name}")
    User getUser(String name);

    @Update("UPDATE user SET userpassword = #{password}, useremail = #{email} WHERE username = #{name}")
    int updateUser(User user);
}