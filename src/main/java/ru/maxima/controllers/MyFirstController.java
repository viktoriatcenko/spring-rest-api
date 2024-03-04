package ru.maxima.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MyFirstController {


    @GetMapping("/hello-endpoint")
    public String sayHello() {
        return "Hello World!";
    }



}
