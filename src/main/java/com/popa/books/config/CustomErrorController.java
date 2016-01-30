package com.popa.books.config;

import com.popa.books.model.api.JsonErrorDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

/**
 * This controller overrides the default /error application path to hijack error messages.
 * After populating the error details a JSON like error response is returned. This can be
 * parsed in JS to display relevant informations.
 * 404 errors are handled by {@link com.popa.books.config.CustomErrorPageHandler}
 */
@RestController
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

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
        Map<String, Object> result = errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
        try {
            String url = new URL(request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    request.getContextPath()).toString();
            url += request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
            result.put("url", url);
            //intentionally skip the params from request.getQueryString() here
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            result.put("url", "error detecting url");
        }
        result.put("method", request.getMethod());
        result.put("serverInfo", request.getServletContext().getServerInfo());
        result.put("realPath", request.getServletContext().getRealPath(""));
        result.put("userAgent", request.getHeader("user-agent"));
        result.put("protocol", request.getProtocol());
        result.put("accept", request.getHeader("accept"));
        StringBuilder parameters = new StringBuilder();
        for (Enumeration<String> en = request.getParameterNames(); en.hasMoreElements(); ) {
            String parameter = en.nextElement();
            parameters.append(parameter).append(": '").append(request.getParameter(parameter)).append("'").append("$$");
        }
        if (parameters.length() > 0) {
            parameters.delete(parameters.lastIndexOf("$$"), parameters.length());
        }
        result.put("parameters", parameters.toString());

        return result;
    }

}
