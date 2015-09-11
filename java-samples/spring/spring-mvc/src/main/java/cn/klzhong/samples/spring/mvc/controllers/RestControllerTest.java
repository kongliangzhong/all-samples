package cn.klzhong.samples.spring.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController  // = @Controller + @ResponseBody
@RequestMapping("/rest")
public class RestControllerTest {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public User test01() {
        System.out.println("path: /rest/test.");
        return new User("aaa", "***");
    }
}

class User {
    private String name;
    private String password;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
