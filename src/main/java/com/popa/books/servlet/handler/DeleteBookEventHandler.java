package com.popa.books.servlet.handler;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.popa.books.dao.Book;
import com.popa.books.dao.persistence.BorgPersistence;

public class DeleteBookEventHandler extends EventHandler {

	private static final Logger logger = Logger.getLogger(DeleteBookEventHandler.class);

	@Override
	public String handleEvent(HttpServletRequest request) throws ServletException {
		EntityManager conn = null;
		try {
			conn = BorgPersistence.getEntityManager();
			conn.getTransaction().begin();
			String bookId = request.getParameter("bookId");
			if (StringUtils.isEmpty(bookId)) {
				logger.error("book id is incorrect: " + bookId);
				throw new ServletException("book id is incorrect: " + bookId);
			}
			Book book = conn.createNamedQuery("Book.findById", Book.class).setParameter("bookId", Long.valueOf(bookId)).getSingleResult();
			conn.remove(book);
			conn.getTransaction().commit();
			return null;
		} catch (Exception exc) {
			if (conn.getTransaction().isActive()) {
				conn.getTransaction().rollback();
			}
			logger.error(exc, exc);
			throw new ServletException(exc);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
