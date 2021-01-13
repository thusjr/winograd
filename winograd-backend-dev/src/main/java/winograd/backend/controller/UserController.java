package winograd.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

import com.alibaba.fastjson.JSON;

import winograd.backend.model.User;
import winograd.backend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private void init() throws SQLException {
		// 连接到SQLite
		String url = "jdbc:sqlite:./userInfo/userInfo.db";
		Connection conn = DriverManager.getConnection(url, "", "");
		Statement stmt = conn.createStatement();
		// 初始化表
		String sql = "CREATE TABLE IF NOT EXISTS user (\n" + " username CHAR(50) PRIMARY KEY NOT NULL,\n"
				+ " userpassword CHAR(50) NOT NULL,\n" + " useremail TEXT\n" + ");";
		stmt.execute(sql);
		stmt.close();
		conn.close();
	}

	UserController() throws SQLException {
		init();
	}

	@Autowired
	private UserService service;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	ResponseEntity<String> login(@RequestBody User userInfo) throws Exception {
		if (this.service.testUserExist(userInfo.getName())
				&& this.service.getPassword(userInfo.getName()).equals(userInfo.getPassword())) {
			User user = this.service.getUser(userInfo.getName());
			return new ResponseEntity<>(JSON.toJSONString(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("LOGIN FAIL.", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	ResponseEntity<String> register(@RequestBody User userInfo) throws Exception {
		if (!this.service.testUserExist(userInfo.getName()) && this.service.createUser(userInfo)) {
			return new ResponseEntity<>("REGISTER SUCESS.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("REGISTER FAIL.", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	ResponseEntity<String> update(@RequestBody User userInfo) throws Exception {
		if (this.service.testUserExist(userInfo.getName()) && this.service.updateUser(userInfo))
			return new ResponseEntity<>("UPDATE SUCESS.", HttpStatus.OK);
		else
			return new ResponseEntity<>("UPDATE FAIL.", HttpStatus.BAD_REQUEST);
	}
}