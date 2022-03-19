package com.merchante.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource(value = {"classpath:properties/environments/${environment}.properties", "classpath:properties/environments/default.properties"}, encoding = "UTF-8")
@ComponentScan(basePackages = "com.merchante")
@Lazy
public class BaseConf {

    @Autowired
    private Environment environment;

    // For a future
    //    @Value("${appium.server}")
    //    private String appiumServerUrl;

    // For a future
    //    @Value("${browserstack.server}")
    //    private String browserStackServerUrl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper;
    }

    // For a future
    //    @Bean
    //    public URL appiumServerUrl() throws MalformedURLException {
    //        return new URL(appiumServerUrl);
    //    }

    // For a future
    //    @Bean
    //    @Primary
    //    public URL browserStackServerUrl() throws MalformedURLException {
    //        return new URL(browserStackServerUrl);
    //    }
}