package com.popa.books.repository;

import com.popa.books.model.Book;
import com.popa.books.model.BookCover;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCoverRepository  extends CrudRepository<BookCover, Long>{

    BookCover findByBook(final Book book);

}
