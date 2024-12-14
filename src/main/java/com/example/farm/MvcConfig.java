package com.example.farm;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Этот класс реализует интерфейс WebMvcConfigurer, что позволяет настраивать различные аспекты веб-приложения.

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // Метод для добавления контроллеров представлений.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

}
