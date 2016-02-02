package com.popa.books.model.api;

import com.popa.books.model.Book;

import java.util.List;

public class BookListWrapper {

    private Long totalCount;
    private List<Book> bookList;

    public BookListWrapper(Long totalCount, List<Book> bookList) {
        this.totalCount = totalCount;
        this.bookList = bookList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
