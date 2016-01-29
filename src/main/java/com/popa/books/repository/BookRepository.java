package com.popa.books.repository;

import com.popa.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    Long countByAuthorIsNull();

    Long countByEdituraIsNull();

    Long countByTitleIsNull();

    String NATIVE_QUERY = "select distinct(substring(b.title, 1,1)), (select count(1) from book b1 where substring(b1.title, 1,1) = substring(b.title, 1,1)) " +
        "FROM book b WHERE b.title IS NOT NULL AND (select count(1) from book b1 where substring(b1.title, 1,1) = substring(b.title, 1,1)) >0";
    @Query(value = NATIVE_QUERY, nativeQuery = true)
    List<Object[]> findBooksAndCount();

}
