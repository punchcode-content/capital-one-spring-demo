package com.theironyard.librarymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import us.dreisbach.NameService;

import java.util.Arrays;

@SpringBootApplication
public class LibraryManagerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(LibraryManagerApplication.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        System.out.println(beanNames.length + " beans");
    }

    @Autowired
    public LibraryManagerApplication(NameService nameService) {
        System.out.println("Hi, my name is " + nameService.getName());
    }
}
