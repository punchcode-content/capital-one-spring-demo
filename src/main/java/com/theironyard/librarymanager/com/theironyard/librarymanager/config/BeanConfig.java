package com.theironyard.librarymanager.com.theironyard.librarymanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.dreisbach.NameService;

@Configuration
public class BeanConfig {
    @Bean
    public NameService nameService() {
        return new NameService();
    }
}
