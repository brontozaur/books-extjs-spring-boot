package com.popa.books;

import com.google.gson.JsonObject;
import com.popa.books.servlet.ResponseKey;
import com.popa.books.servlet.handler.EventHandler;
import com.popa.books.servlet.handler.EventHandlerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BooksController {

    @RequestMapping(value = "/books",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void index(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        EventHandler handler = EventHandlerFactory.getHandler(request);
        try {
            String responseText = handler.handleEvent(request);
            response.setContentType("text/html;charset=UTF-8");
            if (responseText != null) {
                response.getWriter().write(responseText);
            } else {
                JsonObject obj = new JsonObject();
                obj.addProperty("success", true);
                response.getWriter().write(obj.toString());
            }
        } catch (Exception e) {
            handler.processError(e);
            response.addHeader(ResponseKey.ERROR_MESSAGE, handler.getErrorMessage() != null ? handler.getErrorMessage() : "A intervenit o eroare!");
            response.addHeader(ResponseKey.ERROR_ROOT_CAUSE, handler.getErrorRootCause());
            response.addHeader(ResponseKey.ERROR_STACKTRACE, handler.getErrorStackTrace());
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            throw new ServletException(e);
        }
    }

    @RequestMapping(value = "/books",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        index(request, response);
    }

}