package com.popa.books.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.popa.books.dao.persistence.BorgPersistence;

public final class Database {

    private final static Logger logger = Logger.getLogger(Database.class);

    private Database() {
    }

    public static List<AbstractDB> getDbObjectsList(final Class<? extends AbstractDB> clazz, String extraOptions, String persistenceUnitName)
            throws DatabaseException {
        EntityManager em = null;
        try {
            if (StringUtils.isNotEmpty(persistenceUnitName)) {
                em = BorgPersistence.getEntityManager(persistenceUnitName);
            } else {
                em = BorgPersistence.getEntityManager();
            }
            StringBuilder sql = new StringBuilder("FROM " + clazz.getSimpleName());
            if (StringUtils.isNotEmpty(extraOptions)) {
                sql.append(" WHERE ");
                if (extraOptions.trim().toLowerCase().startsWith("and")) {
                    extraOptions = extraOptions.trim().toLowerCase().substring(3);
                }
                sql.append(extraOptions);
            }
            return em.createQuery(sql.toString()).getResultList();
        } catch (Exception e) {
            logger.error(e, e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static List<AbstractDB> getDbObjectsList(final Class<? extends AbstractDB> clazz, final String extraOptions) throws DatabaseException {
        return getDbObjectsList(clazz, extraOptions, null);
    }

    public static List<AbstractDB> getDbObjectsList(final Class<? extends AbstractDB> clazz) throws DatabaseException {
        return getDbObjectsList(clazz, null, null);
    }

    // i had some troubles with this one. Use named queries instead. see Book
    public static AbstractDB getDbObjectById(Class<? extends AbstractDB> clazz, long objectId) throws DatabaseException {
        EntityManager em = null;
        try {
            em = BorgPersistence.getEntityManager();
            return em.find(clazz, objectId);
        } catch (Exception e) {
            logger.error(e, e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static AbstractDB getDbObject(Class<? extends AbstractDB> clazz, String queryOptions) throws DatabaseException {
        EntityManager em = null;
        try {
            em = BorgPersistence.getEntityManager();
            StringBuilder sql = new StringBuilder("FROM " + clazz.getSimpleName());
            sql.append(" WHERE ");
            sql.append(queryOptions);
            List<AbstractDB> results = em.createQuery(sql.toString()).getResultList();
            if (results.size() > 0) {
                return results.get(0);
            }
            return null;
        } catch (Exception e) {
            logger.error(e, e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static int getCount(final Class<? extends AbstractDB> clazz, final String extraOptions, final boolean showQuery) {
        EntityManager em = null;
        try {
            em = BorgPersistence.getEntityManager();
            StringBuilder query = new StringBuilder(100);
            query.append("SELECT count(1) FROM ").append(clazz.getSimpleName());
            if (StringUtils.isNotEmpty(extraOptions)) {
                query.append(extraOptions);
            }
            return (Integer) em.createQuery(query.toString()).getSingleResult();
        } catch (Exception exc) {
            logger.error(exc.getMessage(), exc);
            return 0;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static List<Object[]> getDataObject(final String stringSQL) throws DatabaseException {
        EntityManager em = null;
        try {
            em = BorgPersistence.getEntityManager();
            List<Object[]> result = em.createNativeQuery(stringSQL).getResultList();
            return result;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
