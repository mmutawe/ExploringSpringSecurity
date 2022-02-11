package com.mmutawe.explore.spring.security.exploringspringsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("/login")
    public String getLoginView(){
        return "login";
    }

    @GetMapping("/welcome")
    public String getGreetingMessage() {
        return "welcome";
    }

}
