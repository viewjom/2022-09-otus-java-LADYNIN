package ru.otus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override

    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/news").setViewName("news");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/no-handler-view").setViewName("noHandlerView");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}