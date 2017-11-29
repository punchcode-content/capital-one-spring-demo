package com.theironyard.librarymanager.config;

import com.theironyard.librarymanager.services.AuthorFormatter;
import com.theironyard.librarymanager.services.PublisherFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private PublisherFormatter publisherFormatter;
    private AuthorFormatter authorFormatter;

    @Autowired
    public void setPublisherFormatter(PublisherFormatter publisherFormatter) {
        this.publisherFormatter = publisherFormatter;
    }

    @Autowired
    public void setAuthorFormatter(AuthorFormatter authorFormatter) {
        this.authorFormatter = authorFormatter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(publisherFormatter);
        registry.addFormatter(authorFormatter);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("redirect:/");
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

}
