package cn.klzhong.samples.spring.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    public PageController() {
        System.out.println("PageController init.");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("aa", "bbbb");
        //System.out.println("context root: " + request.getContextPath());
        System.out.println("goto index page.");
        return "index";
    }

    @RequestMapping(value = "/test01", method = RequestMethod.GET)
    @ResponseBody
    public String test01() {
        System.out.println("path: /test01.");
        return "aaaaaaaaaaaaaaaa";
    }
}
