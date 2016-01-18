package com.popa.books.servlet.handler;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.popa.books.dao.AbstractDB;
import com.popa.books.dao.Categorie;
import com.popa.books.dao.Database;
import com.popa.books.dao.DatabaseException;

public class GetCategoriiEventHandler extends EventHandler {

    private static final Logger logger = Logger.getLogger(GetCategoriiEventHandler.class);

    @Override
    public String handleEvent(final HttpServletRequest request) throws ServletException {
        try {
            List<AbstractDB> edituri = Database.getDbObjectsList(Categorie.class);
            System.err.println(new Gson().toJson(edituri));
            return new Gson().toJson(edituri);
        } catch (DatabaseException e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
