package com.popa.books.repository;

import com.popa.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * By extending JpaRepository, we were able to call saveAndFlush() which returns
 * the full inserted object (with generated id). Useful for transactions.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);

}
