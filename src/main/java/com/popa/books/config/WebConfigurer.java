package com.popa.books.config;

import com.popa.books.config.BooksApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * In this file we map the relative path /covers to upload folder, depending on OS.
 * See application properties:
 *      covers.upload.mac.dir=/usr/local/logs
 *      covers.upload.win.dir=c:
 *      covers.upload.path=/covers/
 */
@Component
public class WebConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private BooksApplicationProperties props;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(props.getCoversUploadPath()+ "**").addResourceLocations("file:" + props.getFullUploadDir());
    }

}