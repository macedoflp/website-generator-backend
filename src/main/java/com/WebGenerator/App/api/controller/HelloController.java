package com.WebGenerator.App.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String helloWorld() {
        return "helloWorld"; // This corresponds to the helloWorld.html template
    }
}
