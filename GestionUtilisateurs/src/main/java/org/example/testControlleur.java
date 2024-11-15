package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utilisateurs")
public class testControlleur {
    @GetMapping
    public String sayHello(){
            return "hello world";

}}
