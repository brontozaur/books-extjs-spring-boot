package com.popa.books.controller.book;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.EventHandler;
import com.popa.books.repository.BookRepository;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.popa.books.model.AbstractDB;
import com.popa.books.model.Book;
import org.springframework.beans.factory.annotation.Autowired;

public class GetBooksEventHandler extends EventHandler {
	
	private static final Logger logger = Logger.getLogger(GetBooksEventHandler.class);

//	@Autowired
//    BookRepository repository;

	@Override
	public String handleEvent(HttpServletRequest request) throws ServletException {
		try {
			List<AbstractDB> books =  new ArrayList<>();
//            Iterable<Book> it = repository.findAll();
//            for (Book book: it) {
//                books.add(book);
//            }
			System.err.println(new Gson().toJson(books));
			return new Gson().toJson(books);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}
}
