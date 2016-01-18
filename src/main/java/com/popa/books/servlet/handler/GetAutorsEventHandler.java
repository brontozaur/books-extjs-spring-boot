package com.popa.books.servlet.handler;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.popa.books.dao.AbstractDB;
import com.popa.books.dao.Autor;

public class GetAutorsEventHandler extends EventHandler {
	
	private static final Logger logger = Logger.getLogger(GetAutorsEventHandler.class);

	@Override
	public String handleEvent(HttpServletRequest request) throws ServletException {
		try {
			List<AbstractDB> autors = null;//Database.getDbObjectsList(Autor.class);
			System.err.println(new Gson().toJson(autors));
			return new Gson().toJson(autors);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}
}
