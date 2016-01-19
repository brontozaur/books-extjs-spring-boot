package com.popa.books.controller.book;

import java.io.Serializable;

public class BookDTO implements Serializable {

    private String title;
    private String originalTitle;
    private String dataAparitie;
    private Long idAutor;
    private Integer nrPagini;
    private Integer width;
    private Integer height;
    private String isbn;
    private Boolean citita;
    private String serie;
    private Long idEditura;
    private Long idCategorie;

    public BookDTO() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getDataAparitie() {
        return dataAparitie;
    }

    public void setDataAparitie(String dataAparitie) {
        this.dataAparitie = dataAparitie;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public Integer getNrPagini() {
        return nrPagini;
    }

    public void setNrPagini(Integer nrPagini) {
        this.nrPagini = nrPagini;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Boolean getCitita() {
        return citita;
    }

    public void setCitita(Boolean citita) {
        this.citita = citita;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Long getIdEditura() {
        return idEditura;
    }

    public void setIdEditura(Long idEditura) {
        this.idEditura = idEditura;
    }

    public Long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Long idCategorie) {
        this.idCategorie = idCategorie;
    }
}
