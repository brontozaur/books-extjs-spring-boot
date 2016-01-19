package com.popa.books.model;

import javax.persistence.EntityManager;

public abstract class AbstractDB implements Cloneable {

    public static final String EMPTY = "";

    public AbstractDB() {
        super();
    }

    public abstract AbstractDB cloneObject() throws CloneNotSupportedException;

    public abstract Long getId();

    public void store(EntityManager em) {
        if (getId() == null || getId() <= 0) {
            em.persist(this);
        } else {
            em.merge(this);
        }
    }
}
