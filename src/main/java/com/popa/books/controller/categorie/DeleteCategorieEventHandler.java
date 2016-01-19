package com.popa.books.controller.categorie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.EventHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DeleteCategorieEventHandler extends EventHandler {

    private static final Logger logger = Logger.getLogger(DeleteCategorieEventHandler.class);

    @Override
    public String handleEvent(final HttpServletRequest request) throws ServletException {
        try {
            String idCategorie = request.getParameter("idCategorie");
            if (StringUtils.isEmpty(idCategorie)){
                logger.error("Categorie id is incorrect: "+ idCategorie);
                throw new ServletException("Categorie id is incorrect: "+idCategorie);
            }
//            Categorie Categorie = conn.createNamedQuery("Categorie.findById", Categorie.class).setParameter("idCategorie", Long.valueOf(idCategorie))
//                    .getSingleResult();
//            conn.remove(Categorie);
//            conn.getTransaction().commit();
            return null;
        } catch (Exception exc) {
            logger.error(exc, exc);
            throw new ServletException(exc);
        }
    }
}
