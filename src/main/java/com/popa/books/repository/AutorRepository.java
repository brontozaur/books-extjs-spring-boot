package com.popa.books.repository;

import com.popa.books.model.Autor;
import com.popa.books.model.node.NodeSQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository  extends JpaRepository<Autor, Long> {

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

    String HQL_QUERY_AND_NODE_SQL = "SELECT new com.popa.books.model.node.NodeSQL(a.autorId, a.nume, (SELECT COUNT(1) FROM Book b WHERE b.author.autorId = a.autorId)) FROM Autor a " +
            "where a.nume IS NOT NULL AND ((SELECT COUNT(1) FROM Book b WHERE b.author.autorId = a.autorId) > 0)";
    @Query(value = HQL_QUERY_AND_NODE_SQL)
    List<NodeSQL> findAutorsAndBookCountUsingHQLAndNodeSQL();

}
