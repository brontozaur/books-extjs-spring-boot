package com.popa.books.controller.categorie;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.EventHandler;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.popa.books.model.AbstractDB;

public class GetCategoriiEventHandler extends EventHandler {

    private static final Logger logger = Logger.getLogger(GetCategoriiEventHandler.class);

    @Override
    public String handleEvent(final HttpServletRequest request) throws ServletException {
        try {
            List<AbstractDB> edituri = null;//Database.getDbObjectsList(Categorie.class);
            System.err.println(new Gson().toJson(edituri));
            return new Gson().toJson(edituri);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
