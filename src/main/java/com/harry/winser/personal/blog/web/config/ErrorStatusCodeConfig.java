package com.harry.winser.personal.blog.web.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainer;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorStatusCodeConfig {


    @Bean
    public EmbeddedServletContainerCustomizer errorCodeStatusPages(){
        return (container -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/errorPages/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/500.html");

            container.addErrorPages(error404Page, error500Page);
        });
    }

}
