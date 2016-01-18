package com.popa.books.servlet.handler;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.popa.books.dao.Autor;

public class SaveAutorEventHandler extends EventHandler {

	private static final Logger logger = Logger.getLogger(SaveAutorEventHandler.class);

	@Override
	public String handleEvent(HttpServletRequest request) throws ServletException {
		EntityManager conn = null;
		try {
//			conn = BorgPersistence.getEntityManager();
			conn.getTransaction().begin();
			Autor autor = new Autor();
			String autorId = request.getParameter("autorId");
			if (StringUtils.isNotEmpty(autorId)) {
				autor.setAutorId(Integer.valueOf(autorId));
			}
			String dateParam = request.getParameter("dataNasterii");
			try {
				if (StringUtils.isNotEmpty(dateParam)) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					autor.setDataNasterii(new Date(sdf.parse(dateParam).getTime()));
				}
			} catch (Exception e) {
				logger.error("cannot parse date: " + dateParam);
			}
			autor.setNume(request.getParameter("nume"));
			autor.store(conn);
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
