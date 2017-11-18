package com.theironyard.librarymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LibraryManagerApplication {
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = SpringApplication.run(LibraryManagerApplication.class, args);
        HelloWorld hello = (HelloWorld) ctx.getBean("helloWorld");
        hello.sayHello();
    }
}
