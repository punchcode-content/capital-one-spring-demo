package com.theironyard.librarymanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.theironyard.librarymanager.repositories")
public class BeanConfig {
}
