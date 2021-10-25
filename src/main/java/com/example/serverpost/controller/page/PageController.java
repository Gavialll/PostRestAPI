package com.example.serverpost.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @GetMapping("/home")
    public String home(){
        return "index";
    }

    @GetMapping("/account")
    public String account(){
        return "account";
     }

    @GetMapping("/authorization")
    public String authorization(){
        return "login";
    }

    @GetMapping("/post")
    public String post(){
        return "post";
    }
}
