package com.theironyard.librarymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import us.dreisbach.NameService;

@SpringBootApplication
@ComponentScan("us.dreisbach")
public class LibraryManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerApplication.class, args);
    }

    @Autowired
    public LibraryManagerApplication(NameService nameService) {
        System.out.println("Hi, my name is " + nameService.getName());
    }
}
