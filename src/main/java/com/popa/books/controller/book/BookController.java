package com.popa.books.controller.book;

import com.popa.books.controller.Events;
import com.popa.books.model.AbstractDB;
import com.popa.books.model.Book;
import com.popa.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookRepository repository;

    @RequestMapping("/booksaas")
    public List<Book> getAllBooks(@RequestParam String event){
        if (Events.GET_BOOKS.equals(event)) {
            List<Book> books =  new ArrayList<>();
            Iterable<Book> it = repository.findAll();
            for (Book book: it) {
                books.add(book);
            }
            return books;
        }
        return null;
    }

/*
    @RequestMapping(value = "/getbooks",
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

    @RequestMapping(value = "/getbooks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        index(request, response);
    }*/
}
