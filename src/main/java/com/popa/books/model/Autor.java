package com.popa.books.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "autor")
@NamedQuery(name = "Autor.findById", query = "select a from Autor a where a.autorId = :autorId")
public class Autor extends AbstractDB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "autorId", nullable = false, unique = true)
    private Long autorId;
    @Column(name = "nume", unique = true)
    private String nume = EMPTY;
    @Column(name = "dataNasterii")
    private Date dataNasterii = null;

    @Override
    public Autor cloneObject() throws CloneNotSupportedException {
        return (Autor) this.clone();
    }

    public long getAutorId() {
        return autorId;
    }

    public void setAutorId(long autorId) {
        this.autorId = autorId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public Long getId() {
        return this.autorId;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

}
