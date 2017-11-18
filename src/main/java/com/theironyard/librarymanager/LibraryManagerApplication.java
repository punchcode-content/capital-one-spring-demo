package com.theironyard.librarymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LibraryManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerApplication.class, args);
    }

    @Autowired
    public LibraryManagerApplication(HelloWorld _hello) {
        _hello.sayHello();
    }

    private HelloWorld hello;

    @Autowired
    public void setHello(HelloWorld hello) {
        this.hello = hello;
    }

    @PostConstruct
    public void init() {
        hello.sayHello();
    }
}
