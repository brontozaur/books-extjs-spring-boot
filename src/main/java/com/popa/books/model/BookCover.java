package com.popa.books.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "book_cover")
public class BookCover implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookCoverId", nullable = false, unique = true)
    private Long bookCoverId;

    @Column(name = "front")
    @Lob
    private byte[] front;

    @Column(name = "back")
    @Lob
    private byte[] back;

    public Long getBookCoverId() {
        return bookCoverId;
    }

    public void setBookCoverId(Long bookCoverId) {
        this.bookCoverId = bookCoverId;
    }

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
}
