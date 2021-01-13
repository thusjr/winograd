package winograd.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @RequestMapping("/")
	String home(){
		return "hello spring boot!";
	}
}