package com.springsecurity;

import com.springsecurity.core.configurations.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
public class MmsAuthApplication {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(MmsAuthApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return appConfig;
    }
}
