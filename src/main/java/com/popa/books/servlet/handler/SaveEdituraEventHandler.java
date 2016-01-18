package com.popa.books.servlet.handler;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.popa.books.dao.Editura;

public class SaveEdituraEventHandler extends EventHandler {

	private static final Logger logger = Logger.getLogger(SaveEdituraEventHandler.class);

	@Override
	public String handleEvent(HttpServletRequest request) throws ServletException {
		EntityManager conn = null;
		try {
			conn.getTransaction().begin();
			Editura editura = new Editura();
			String idEditura = request.getParameter("idEditura");
			if (StringUtils.isNotEmpty(idEditura)) {
				editura.setIdEditura(Integer.valueOf(idEditura));
			}
			editura.setNumeEditura(request.getParameter("numeEditura"));
			editura.store(conn);
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
