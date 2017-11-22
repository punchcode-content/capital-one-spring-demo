package com.theironyard.librarymanager.config;

import com.theironyard.librarymanager.services.PublisherFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private PublisherFormatter publisherFormatter;

    @Autowired
    public void setPublisherFormatter(PublisherFormatter publisherFormatter) {
        this.publisherFormatter = publisherFormatter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(publisherFormatter);
    }
}
