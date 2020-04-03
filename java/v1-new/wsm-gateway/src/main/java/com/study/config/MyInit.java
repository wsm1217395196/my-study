package com.study.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyInit implements CommandLineRunner {

    @Autowired
    private MyReactiveAuthorizationManager myReactiveAuthorizationManager;

    @Override
    public void run(String... args) {
        myReactiveAuthorizationManager.loadResourceDefine();
    }

}


