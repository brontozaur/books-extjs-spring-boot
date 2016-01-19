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
@Table(name = "categorie")
@NamedQuery(name = "Categorie.findById", query = "select c from Categorie c where c.idCategorie = :idCategorie")
public class Categorie extends AbstractDB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCategorie", nullable = false, unique = true)
    private Long idCategorie;
    @Column(name = "numeCategorie", unique = true)
    private String numeCategorie = EMPTY;

    @Override
    public Categorie cloneObject() throws CloneNotSupportedException {
        return (Categorie) this.clone();
    }

    public String getNumeCategorie() {
        return numeCategorie;
    }

    public void setNumeCategorie(final String numeCategorie) {
        this.numeCategorie = numeCategorie;
    }

    @Override
    public Long getId() {
        return this.idCategorie;
    }

    public long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(final long idCategorie) {
        this.idCategorie = idCategorie;
    }

}
