package com.popa.books.dao;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "book")
@NamedQuery(name = "Book.findById", query = "select b from Book b where b.bookId = :bookId")
public class Book extends AbstractDB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookId", nullable = false, unique = true)
    private Long bookId;
    @Column(name = "title")
    private String title;
    @Column(name = "originalTitle")
    private String originalTitle;
    @Column(name = "dataAparitie")
    private Date dataAparitie = null;
    @OneToOne
    @JoinColumn(name = "idAutor")
    private Autor author;
    @Column(name = "nrPagini")
    private Integer nrPagini;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "citita")
    private Boolean citita;
    @Column(name = "serie")
    private String serie;
    @OneToOne
    @JoinColumn(name = "idEditura")
    private Editura editura;
    @OneToOne
    @JoinColumn(name = "idCategorie")
    private Categorie categorie;

    @Override
    public Book cloneObject() throws CloneNotSupportedException {
        return (Book) this.clone();
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(final long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Date getDataAparitie() {
        return dataAparitie;
    }

    public void setDataAparitie(final Date dataAparitie) {
        this.dataAparitie = dataAparitie;
    }

    public Autor getAuthor() {
        return author;
    }

    public void setAuthor(final Autor author) {
        this.author = author;
    }

    @Override
    public Long getId() {
        return this.bookId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getNrPagini() {
        return nrPagini;
    }

    public void setNrPagini(final int nrPagini) {
        this.nrPagini = nrPagini;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public boolean isCitita() {
        return citita;
    }

    public void setCitita(final boolean citita) {
        this.citita = citita;
    }

    public Editura getEditura() {
        return editura;
    }

    public void setEditura(final Editura editura) {
        this.editura = editura;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(final Categorie categorie) {
        this.categorie = categorie;
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

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setNrPagini(Integer nrPagini) {
		this.nrPagini = nrPagini;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}
