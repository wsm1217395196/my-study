package com.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

//    @Autowired
//    private Principal principal;

    @GetMapping
    public String getName(){
//        String name = principal.getName();
//        System.out.println("name = " + name);
        return "网帅逼";
    }
}
