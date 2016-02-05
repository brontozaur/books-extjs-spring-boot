package com.popa.books.repository;

import com.popa.books.model.Editura;
import com.popa.books.model.node.NodeSQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdituraRepository  extends JpaRepository<Editura, Long> {

    String HQL_QUERY_AND_NODE_SQL = "SELECT new com.popa.books.model.node.NodeSQL(e.idEditura, e.numeEditura, (SELECT COUNT(1) FROM Book b WHERE b.editura.idEditura = e.idEditura)) " +
            "FROM Editura e WHERE e.numeEditura IS NOT NULL AND (SELECT COUNT(1) FROM Book b WHERE b.editura.idEditura = e.idEditura) >0";
    @Query(value = HQL_QUERY_AND_NODE_SQL)
    List<NodeSQL> findEdituriAndBookCount();

}
