package com.popa.books.repository;

import com.popa.books.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends CrudRepository<Book, Long>{

    List<Book> findByTitle(String titles);

}
