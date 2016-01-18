package com.popa.books.repository;

import com.popa.books.dao.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository  extends CrudRepository<Book, Long>{


}
