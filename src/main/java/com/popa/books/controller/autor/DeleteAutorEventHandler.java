package com.popa.books.controller.autor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.EventHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DeleteAutorEventHandler extends EventHandler {

	private static final Logger logger = Logger.getLogger(DeleteAutorEventHandler.class);

	@Override
	public String handleEvent(HttpServletRequest request) throws ServletException {
		try {
			String autorId = request.getParameter("autorId");
			if (StringUtils.isEmpty(autorId)){
				logger.error("autor id is incorrect: "+ autorId);
				throw new ServletException("autor id is incorrect: "+autorId);
			}
//			Autor autor = conn.createNamedQuery("Autor.findById", Autor.class).setParameter("autorId", Long.valueOf(autorId))
//					.getSingleResult();
//			conn.remove(autor);
//			conn.getTransaction().commit();
			return null;
		} catch (Exception exc) {
			logger.error(exc, exc);
			throw new ServletException(exc);
		}
	}
}
