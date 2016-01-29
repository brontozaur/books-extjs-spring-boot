package com.popa.books.repository;

import com.popa.books.model.Autor;
import com.popa.books.model.node.AutorNodeSQL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository  extends CrudRepository<Autor, Long>{

    /**
     * We need to execute some rather strange aggregation queries to get the necessary data for author left tree.
     *
     * Query#1: SELECT a.nume, (SELECT COUNT(1) FROM Book b WHERE b.idAutor = a.autorId) AS bookCount FROM Autor a
     *
     * Since JPA doesn't directly support multiple column value retrieval, we'll do some hacks.
     */

    //solution#1 - using a native query. Not recommended (here <books> and <autor> are tables)

    String NATIVE_QUERY = "SELECT a.nume, (SELECT COUNT(1) FROM book b WHERE b.id_autor = a.autor_id) AS bookCount FROM autor a";
    @Query(value = NATIVE_QUERY, nativeQuery = true)
    List<Object[]> findAutorsAndBookCountUsingNativeQuery();

    //solution#2 - using HQL with List<Object[]>
    String HQL_QUERY =  "SELECT a.nume, (SELECT COUNT(1) FROM Book b WHERE b.author.autorId = a.autorId) AS bookCount FROM Autor a";
    @Query(value=HQL_QUERY)
    public List<Object[]> findProjects();

    //solution#3 - using HQL query with custom DTO. Definately the best one!

    String HQL_QUERY_AND_NODE_SQL = "SELECT new com.popa.books.model.node.AutorNodeSQL(a.nume, (SELECT COUNT(1) FROM Book b WHERE b.author.autorId = a.autorId)) FROM Autor a";
    @Query(value = HQL_QUERY_AND_NODE_SQL)
    List<AutorNodeSQL> findAutorsAndBookCountUsingHQLAndNodeSQL();

    /**
     * Now we use only AutorNodeSQL
     */

//    String COUNT_BOOKS_AND_AUTHORS = "SELECT " +
//            "new com.popa.books.model.node.AutorNodeSQL((SELECT SUBSTRING(a.nume,1,1)) AS firstLetter, " +
//            "(SELECT COUNT(1) FROM Autor a1 WHERE SUBSTRING(a1.nume,1,1) = firstLetter) AS autorsNumber" +
//            "(SELECT COUNT(1) FROM Book b WHERE b.author.autorId IN (select a2.autorId from Autor a2 where substring(a2.nume, 1,1) = firstLetter)) AS booksNumber)" +
//            " FROM Autor a group by firstLetter";
//    @Query(value = COUNT_BOOKS_AND_AUTHORS)
//    List<AutorNodeSQL> findAuthorsAndBooksAndGroupByLetter();

    String COUNT_BOOKS_AND_AUTHORS = "SELECT SUBSTRING(a.nume,1,1) as firstLetter," +
            "(SELECT COUNT(1) FROM Autor a1 WHERE SUBSTRING(a1.nume,1,1) = firstLetter) AS autorsNumber," +
            "(SELECT COUNT(1) FROM Book b WHERE b.id_autor IN (select a2.autor_id from autor a2 where substring(a2.nume, 1,1) = firstLetter)) AS booksNumber FROM Autor a group by firstLetter";
    @Query(value = COUNT_BOOKS_AND_AUTHORS, nativeQuery = true)
    List<Object[]> findAuthorsAndBooksAndGroupByLetter();
}
