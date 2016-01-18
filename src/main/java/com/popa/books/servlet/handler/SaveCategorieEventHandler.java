package com.popa.books.servlet.handler;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.popa.books.dao.Categorie;

public class SaveCategorieEventHandler extends EventHandler {

	private static final Logger logger = Logger.getLogger(SaveCategorieEventHandler.class);

	@Override
	public String handleEvent(final HttpServletRequest request) throws ServletException {
		EntityManager conn = null;
		try {
			conn.getTransaction().begin();
			Categorie categorie = new Categorie();
			String idCategorie = request.getParameter("idCategorie");
			if (StringUtils.isNotEmpty(idCategorie)) {
				categorie.setIdCategorie(Integer.valueOf(idCategorie));
			}
			categorie.setNumeCategorie(request.getParameter("numeCategorie"));
			categorie.store(conn);
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
