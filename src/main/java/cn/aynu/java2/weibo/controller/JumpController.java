package cn.aynu.java2.weibo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tianh
 */
@Controller
public class JumpController {
    @RequestMapping("/profile/{id}")
    public String jumpProfile(@PathVariable String id, Model m){
        m.addAttribute("id",id);
        return "/profile";
    }
}
