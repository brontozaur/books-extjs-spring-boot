package com.popa.books.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "book_cover")
@NamedQueries(value= {
        @NamedQuery(name = "BookCover.findFrontCover", query = "select b.front from BookCover b where b.book.id = :bookId"),
        @NamedQuery(name = "BookCover.findBackCover", query = "select b.back from BookCover b where b.book.bookId = :bookId"),
        @NamedQuery(name = "BookCover.findByBookId", query = "select b from BookCover b where b.book.bookId = :bookId")
})
public class BookCover extends AbstractDB implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String QUERY_GET_FRONT = "BookCover.findFrontCover";
    public static String QUERY_GET_BACK = "BookCover.findBackCover";
    public static String QUERY_GET_BY_BOOK_ID = "BookCover.findByBookId";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookCoverId", nullable = false, unique = true)
    private Long bookCoverId;

    @OneToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @Column(name = "front")
    @Lob
    private byte[] front;


    @Column(name = "back")
    @Lob
    private byte[] back;

    public byte[] getFront() {
        return front;
    }

    public void setFront(byte[] front) {
        this.front = front;
    }

    public byte[] getBack() {
        return back;
    }

    public void setBack(byte[] back) {
        this.back = back;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public BookCover cloneObject() throws CloneNotSupportedException {
        return (BookCover) this.clone();
    }

    @Override
    public Long getId() {
        return this.bookCoverId;
    }
}
