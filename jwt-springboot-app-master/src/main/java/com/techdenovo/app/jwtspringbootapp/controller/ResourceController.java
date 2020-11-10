package com.techdenovo.app.jwtspringbootapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200")
public class ResourceController {
    @GetMapping("/hiadmin")
    public String welcomeadmin(){
        return  "hello admin";
    }

    @GetMapping("/hiuser")
    public String welcomeuser(){
        return  "hellouser";
    }
}
