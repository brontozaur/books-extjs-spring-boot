package com.popa.books.controller;

import com.popa.books.config.BooksApplicationProperties;
import com.popa.books.model.api.JsonErrorDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This controller overrides the default /error application path to hijack error messages.
 * After populating the error details a JSON like error response is returned. This can be
 * parsed in JS to display relevant informations.
 * 404 errors are handled by {@link com.popa.books.config.CustomErrorPageHandler}
 */
@RestController
public class CustomErrorController implements ErrorController{

    private static final String PATH = "/error";

    @Autowired
    private BooksApplicationProperties props;

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH)
    private JsonErrorDescriptor error(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
        return new JsonErrorDescriptor(response.getStatus(), getErrorAttributes(request, props.isIncludingStacktrace()));
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

}
