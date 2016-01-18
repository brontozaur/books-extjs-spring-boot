package com.popa.books.dao.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class BorgPersistence {

    private static Map<String, EntityManagerFactory> factories = new HashMap<String, EntityManagerFactory>();
    public static String DEFAULT_PERSISTENCE_UNIT = "books_db";
    private static Logger logger = Logger.getLogger(BorgPersistence.class);

    public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
        EntityManagerFactory factory = factories.get(persistenceUnitName);
        if (factory != null) {
            if (!factory.isOpen()) {
                factory = Persistence.createEntityManagerFactory(persistenceUnitName);
                factories.put(persistenceUnitName, factory);
            }
        } else {
            factory = Persistence.createEntityManagerFactory(persistenceUnitName);
            factories.put(persistenceUnitName, factory);
        }
        return factory;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return getEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT);
    }

    public static EntityManager getEntityManager(final String persistenceUnitName) {
        EntityManagerFactory factory = getEntityManagerFactory(persistenceUnitName);
        return factory.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return getEntityManager(DEFAULT_PERSISTENCE_UNIT);
    }

    public static void closeFactory(String persistenceUnitName) {
        logger.info("closing EntityManagerFactory: " + persistenceUnitName);
        EntityManagerFactory factory = getEntityManagerFactory(persistenceUnitName);
        if (factory.isOpen()) {
            factory.close();
        }
        factories.remove(persistenceUnitName);
        logger.info("EntityManagerFactory " + persistenceUnitName + " succesfully closed");
    }

    public static void closeAllFactories() {
        logger.info("closing all factories....factories size: " + factories.size());
        List<String> factoryNames = new ArrayList<String>();
        factoryNames.addAll(factories.keySet());
        for (Iterator<String> it = factoryNames.iterator(); it.hasNext();) {
            closeFactory(it.next());
        }
        logger.info("all factories were successfully closed.");
    }

    public static boolean isLoaded(Object object) {
        return Persistence.getPersistenceUtil().isLoaded(object);
    }

    public static boolean isFieldLoaded(Object object, String fieldName) {
        return Persistence.getPersistenceUtil().isLoaded(object, fieldName);
    }

    public static void reloadObject(EntityManager manager, Object entity) throws EntityNotFoundException {
        manager.refresh(entity);
    }

    public static Timestamp getServerTimestamp() {
        return getServerTimestamp(DEFAULT_PERSISTENCE_UNIT);
    }

    public static Timestamp getServerTimestamp(String persistenceUnitName) {
        String serverTimeQuery = "SELECT now()";
        EntityManager em = null;
        try {
            em = BorgPersistence.getEntityManager();
            Query query = em.createNativeQuery(serverTimeQuery);
            return Timestamp.valueOf(query.getSingleResult().toString());
        } finally {
            em.close();
        }
    }
}
