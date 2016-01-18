package com.popa.books.servlet.handler;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.popa.books.dao.AbstractDB;
import com.popa.books.dao.Editura;

public class GetEdituriEventHandler extends EventHandler {
	
	private static final Logger logger = Logger.getLogger(GetEdituriEventHandler.class);

	@Override
	public String handleEvent(HttpServletRequest request) throws ServletException {
		try {
			List<AbstractDB> edituri = null;//Database.getDbObjectsList(Editura.class);
			System.err.println(new Gson().toJson(edituri));
			return new Gson().toJson(edituri);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}
}
