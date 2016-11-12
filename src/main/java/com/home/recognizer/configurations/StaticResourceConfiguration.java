package com.home.recognizer.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

    @Value("${house.images.directory}")
    private String houseImagesDirectory;

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceConfiguration.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(houseImagesDirectory).toFile().getAbsolutePath();
        logger.info("Using the following image directory: {}, corresponds to absolute path: {}", houseImagesDirectory, absolutePath);
        try {
            Files.createDirectories(Paths.get(houseImagesDirectory));
        } catch (IOException e) {
            throw new RuntimeException("Could not create the image directory ("+ absolutePath +"), fatal exception!");
        }
        registry.addResourceHandler("/static/house/images/**").addResourceLocations(Paths.get(houseImagesDirectory).toUri().toString());
    }
}
