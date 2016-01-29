package com.popa.books.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * We map only 404 errors to an error page. Other errors will be handled by the
 * {@linkplain com.popa.books.controller.CustomErrorController}
 */
@Component
public class CustomErrorPageHandler implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");

        container.addErrorPages(error404Page);
    }
}
