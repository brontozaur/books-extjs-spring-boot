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
public class Editura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idEditura", nullable = false, unique = true)
    private Long idEditura;

    @Column(name = "numeEditura", unique = true)
    private String numeEditura = "";

    public Long getIdEditura() {
        return idEditura;
    }

    public void setIdEditura(Long idEditura) {
        this.idEditura = idEditura;
    }

    public String getNumeEditura() {
        return numeEditura;
    }

    public void setNumeEditura(String numeEditura) {
        this.numeEditura = numeEditura;
    }
}
