package com.popa.books.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "editura")
@NamedQuery(name = "Editura.findById", query = "select e from Editura e where e.idEditura = :idEditura")
public class Editura extends AbstractDB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idEditura", nullable = false, unique = true)
    private Long idEditura;
    @Column(name = "numeEditura", unique = true)
    private String numeEditura = EMPTY;

    @Override
    public Editura cloneObject() throws CloneNotSupportedException {
        return (Editura) this.clone();
    }

    public String getNumeEditura() {
        return numeEditura;
    }

    public void setNumeEditura(String numeEditura) {
        this.numeEditura = numeEditura;
    }

    @Override
    public Long getId() {
        return this.idEditura;
    }

    public long getIdEditura() {
        return idEditura;
    }

    public void setIdEditura(long idEditura) {
        this.idEditura = idEditura;
    }

}
