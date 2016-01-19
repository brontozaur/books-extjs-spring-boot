package com.popa.books.controller.editura;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.EventHandler;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.popa.books.model.AbstractDB;

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
