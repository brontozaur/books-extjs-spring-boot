package com.popa.books.servlet.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.popa.books.repository.BookRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.popa.books.dao.Book;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteBookEventHandler extends EventHandler {

	private static final Logger logger = Logger.getLogger(DeleteBookEventHandler.class);

	@Autowired
	private BookRepository repository;

	@Override
	@Transactional
	public String handleEvent(HttpServletRequest request) throws ServletException {
		try {
			String bookId = request.getParameter("bookId");
			if (StringUtils.isEmpty(bookId)) {
				logger.error("book id is incorrect: " + bookId);
				throw new ServletException("book id is incorrect: " + bookId);
			}
			Book book = repository.findOne(Long.valueOf("bookId"));
			repository.delete(book);
			return null;
		} catch (Exception exc) {
			logger.error(exc, exc);
			throw new ServletException(exc);
		}
	}
}
