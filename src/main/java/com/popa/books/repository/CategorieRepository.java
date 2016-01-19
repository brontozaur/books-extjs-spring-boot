package com.popa.books.repository;

import com.popa.books.model.Categorie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository  extends CrudRepository<Categorie, Long>{


}
