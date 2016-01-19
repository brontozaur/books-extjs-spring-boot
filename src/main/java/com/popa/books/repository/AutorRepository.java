package com.popa.books.repository;

import com.popa.books.model.Autor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository  extends CrudRepository<Autor, Long>{


}
