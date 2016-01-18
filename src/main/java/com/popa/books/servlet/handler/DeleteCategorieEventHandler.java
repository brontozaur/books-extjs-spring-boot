package com.popa.books.servlet.handler;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.popa.books.dao.Categorie;
import com.popa.books.dao.persistence.BorgPersistence;

public class DeleteCategorieEventHandler extends EventHandler {

    private static final Logger logger = Logger.getLogger(DeleteCategorieEventHandler.class);

    @Override
    public String handleEvent(final HttpServletRequest request) throws ServletException {
        EntityManager conn = null;
        try {
            conn = BorgPersistence.getEntityManager();
            conn.getTransaction().begin();
            String idCategorie = request.getParameter("idCategorie");
            if (StringUtils.isEmpty(idCategorie)){
                logger.error("Categorie id is incorrect: "+ idCategorie);
                throw new ServletException("Categorie id is incorrect: "+idCategorie);
            }
            Categorie Categorie = conn.createNamedQuery("Categorie.findById", Categorie.class).setParameter("idCategorie", Long.valueOf(idCategorie))
                    .getSingleResult();
            conn.remove(Categorie);
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
