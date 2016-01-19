package com.popa.books.model;

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
    @Column(name = "originalTitle", nullable = true)
    private String originalTitle;
    @Column(name = "dataAparitie", nullable = true)
    private Date dataAparitie = null;
    @OneToOne
    @JoinColumn(name = "idAutor")
    private Autor author;
    @Column(name = "nrPagini", nullable = true)
    private Integer nrPagini;
    @Column(name = "width", nullable = true)
    private Integer width;
    @Column(name = "height", nullable = true)
    private Integer height;
    @Column(name = "isbn", nullable = true)
    private String isbn;
    @Column(name = "citita", nullable = true)
    private Boolean citita;
    @Column(name = "serie", nullable = true)
    private String serie;
    @OneToOne
    @JoinColumn(name = "idEditura", nullable = true)
    private Editura editura;
    @OneToOne
    @JoinColumn(name = "idCategorie", nullable = true)
    private Categorie categorie;

    @Override
    public Book cloneObject() throws CloneNotSupportedException {
        return (Book) this.clone();
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
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
