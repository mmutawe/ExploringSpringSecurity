package com.mmutawe.explore.spring.security.exploringspringsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {


    @GetMapping
    public String getGreetingMessage(){
        return "Hello! here we test spring security :)";
    }
}
