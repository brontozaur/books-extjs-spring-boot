package com.popa.books.repository;

import com.popa.books.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * By extending JpaRepository, we were able to call saveAndFlush() which returns
 * the full inserted object (with generated id). Useful for transactions.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Not used, just for amazing amusement ;-)
     * @param title
     * @return
     */
    List<Book> findByTitle(String title);

    /**
     * Gets the books with no title
     * @param pageable
     * @return
     */
    Page<Book> findByTitleIsNull(Pageable pageable);

    /**
     * Gets the books that starts with the <code>title</code> string
     * @param pageable
     * @param title
     * @return
     */
    Page<Book> findByTitleStartingWith(Pageable pageable, String title);

    /**
     * Gets books with no author
     * @param pageable
     * @return
     */
    Page<Book> findByAuthorIsNull(Pageable pageable);

    /**
     * Gets books with a specific author
     * @param pageable
     * @param authorId
     * @return
     */
    Page<Book> findByAuthorAutorId(Pageable pageable, Long authorId);

    /**
     * Gets books with no editura
     * @param pageable
     * @return
     */
    Page<Book> findByEdituraIsNull(Pageable pageable);

    /**
     * Gets books with a specific editura
     * @param pageable
     * @param idEditura
     * @return
     */
    Page<Book> findByEdituraIdEditura(Pageable pageable, Long idEditura);

    /**
     * Counts the books with no author. Used only to display the book count on the ui
     * @return
     */
    Long countByAuthorIsNull();

    /**
     * Counts the books with no editura. Used only to display the book count on the ui
     * @return
     */
    Long countByEdituraIsNull();

    /**
     * Counts the books with no title. Used only to display the book count on the ui
     * @return
     */
    Long countByTitleIsNull();

    /**
     * Ugly query that groups the books based on the first letter of their title, each letter having a book count
     */
    String NATIVE_QUERY = "select distinct(substring(b.title, 1,1)), (select count(1) from book b1 where substring(b1.title, 1,1) = substring(b.title, 1,1)) " +
        "FROM book b WHERE b.title IS NOT NULL AND (select count(1) from book b1 where substring(b1.title, 1,1) = substring(b.title, 1,1)) >0";
    @Query(value = NATIVE_QUERY, nativeQuery = true)
    List<Object[]> findBooksAndCount();

}
